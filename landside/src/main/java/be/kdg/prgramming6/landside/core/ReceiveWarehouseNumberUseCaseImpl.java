package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberCommand;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberUseCase;
import be.kdg.prgramming6.landside.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReceiveWarehouseNumberUseCaseImpl implements ReceiveWarehouseNumberUseCase {
    private final UpdateWeighbridgePort updateWeighbridgePort;
    private final UpdateWarehousePort updateWarehousePort;
    private final LoadWarehousePort loadWarehousePort;
    private final LoadWeighbridgePort loadWeighbridgePort;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveWarehouseNumberUseCaseImpl.class);

    public ReceiveWarehouseNumberUseCaseImpl(UpdateWeighbridgePort updateWeighbridgePort,
                                             UpdateWarehousePort updateWarehousePort,
                                             LoadWarehousePort loadWarehousePort,
                                             LoadWeighbridgePort loadWeighbridgePort) {
        this.updateWeighbridgePort = updateWeighbridgePort;
        this.updateWarehousePort = updateWarehousePort;
        this.loadWarehousePort = loadWarehousePort;
        this.loadWeighbridgePort = loadWeighbridgePort;
    }

    @Override
    public WarehouseId receiveWarehouseNumber(ReceiveWarehouseNumberCommand command) {
        // Generate WeighbridgeNumber using LicensePlate
        WeighbridgeNumber weighbridgeNumber = WeighbridgeNumber.generate(new LicensePlate(command.licensePlate()));

        // Load the weighbridge by weighbridge number
        Weighbridge weighbridge = loadWeighbridgePort.loadWeighbridge(weighbridgeNumber)
                .orElseThrow(() -> new IllegalArgumentException("Weighbridge not found with number: " + weighbridgeNumber));

        // Create a new Truck object
        Truck truck = new Truck(new LicensePlate(command.licensePlate()), MaterialType.valueOf(command.materialType()), null, new SellerId(command.sellerId()));

        // Set the current truck on the weighbridge
        weighbridge.setCurrentTruck(truck);

        // Load the warehouse by sellerId, materialType, and timestamp
        Warehouse warehouse = loadWarehousePort.loadWarehouse(new SellerId(command.sellerId()), MaterialType.valueOf(command.materialType()), LocalDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for given parameters."));

        // Save updated state back to the repositories
        updateWeighbridgePort.updateWeighbridge(weighbridge);
        updateWarehousePort.updateWarehouse(warehouse);

        // Log the warehouseId and weighbridgeNumber
        LOGGER.info("Warehouse ID: {}", warehouse.getWarehouseId());
        LOGGER.info("Weighbridge Number: {}", weighbridge.getWeighbridgeNumber());

        return warehouse.getWarehouseId();
    }
}