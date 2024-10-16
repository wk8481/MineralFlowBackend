package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberCommand;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberResponse;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberUseCase;
import be.kdg.prgramming6.landside.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class ReceiveWarehouseNumberUseCaseImpl implements ReceiveWarehouseNumberUseCase {
    private final UpdateWeighbridgePort updateWeighbridgePort;
    private final UpdateWarehousePort updateWarehousePort;
    private final LoadWarehousePort loadWarehousePort;
    private final LoadWeighbridgePort loadWeighbridgePort;
    private final LoadTruckPort loadTruckPort;
    private final SaveWBTPort saveWBTPort;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveWarehouseNumberUseCaseImpl.class);

    public ReceiveWarehouseNumberUseCaseImpl(UpdateWeighbridgePort updateWeighbridgePort,
                                             UpdateWarehousePort updateWarehousePort,
                                             LoadWarehousePort loadWarehousePort,
                                             LoadWeighbridgePort loadWeighbridgePort,
                                             LoadTruckPort loadTruckPort,
                                             SaveWBTPort saveWBTPort) {
        this.updateWeighbridgePort = updateWeighbridgePort;
        this.updateWarehousePort = updateWarehousePort;
        this.loadWarehousePort = loadWarehousePort;
        this.loadWeighbridgePort = loadWeighbridgePort;
        this.loadTruckPort = loadTruckPort;
        this.saveWBTPort = saveWBTPort;
    }

    @Override
    public ReceiveWarehouseNumberResponse receiveWarehouseNumber(ReceiveWarehouseNumberCommand command) {
        // Generate WeighbridgeNumber using LicensePlate
        WeighbridgeNumber weighbridgeNumber = WeighbridgeNumber.generate(new LicensePlate(command.licensePlate()));

        // Load the weighbridge by weighbridge number
        Weighbridge weighbridge = loadWeighbridgePort.loadWeighbridge(weighbridgeNumber)
                .orElseThrow(() -> new IllegalArgumentException("Weighbridge not found with number: " + weighbridgeNumber));

        // Load the truck by license plate
        Truck truck = loadTruckPort.loadTruckByLicensePlate(new LicensePlate(command.licensePlate()))
                .orElseThrow(() -> new IllegalArgumentException("Truck not found with license plate: " + command.licensePlate()));

        // Load the warehouse by sellerId, materialType, and timestamp
        Warehouse warehouse = loadWarehousePort.loadWarehouse(new SellerId(command.sellerId()), MaterialType.valueOf(command.materialType()), LocalDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for given parameters."));

        // Generate a random net weight
        BigDecimal netWeight = command.netWeight();

        // Calculate the gross weight
        BigDecimal grossWeight = truck.getTareWeight().add(netWeight);

        // Create a partial WeighbridgeTicket
        WeighbridgeTicket partialTicket = new WeighbridgeTicket(
                command.licensePlate(),
                grossWeight,
                truck.getTareWeight(),
                LocalDateTime.now()
        );

        // Save the partial ticket
        saveWBTPort.save(partialTicket);

        // Save updated state back to the repositories
        updateWeighbridgePort.updateWeighbridge(weighbridge);
        updateWarehousePort.updateWarehouse(warehouse);

        // Log the warehouseId and weighbridgeNumber
        LOGGER.info("Warehouse ID: {}", warehouse.getWarehouseId());
        LOGGER.info("Weighbridge Number: {}", weighbridge.getWeighbridgeNumber());

        return new ReceiveWarehouseNumberResponse(warehouse.getWarehouseId(), weighbridge.getWeighbridgeNumber());
    }
}