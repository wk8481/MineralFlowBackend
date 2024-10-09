package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberCommand;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberUseCase;
import be.kdg.prgramming6.landside.port.out.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class ReceiveWarehouseNumberUseCaseImpl implements ReceiveWarehouseNumberUseCase {
    private final UpdateWeighbridgePort updateWeighbridgePort;
    private final UpdateWarehousePort updateWarehousePort;
    private final UpdateTruckPort updateTruckPort;
    private final LoadWarehousePort loadWarehousePort;
    private final LoadTruckPort loadTruckPort;
    private final LoadWeighbridgePort loadWeighbridgePort;

    public ReceiveWarehouseNumberUseCaseImpl(UpdateWeighbridgePort updateWeighbridgePort,
                                             UpdateWarehousePort updateWarehousePort,
                                             UpdateTruckPort updateTruckPort,
                                             LoadWarehousePort loadWarehousePort,
                                             LoadTruckPort loadTruckPort,
                                             LoadWeighbridgePort loadWeighbridgePort) {
        this.updateWeighbridgePort = updateWeighbridgePort;
        this.updateWarehousePort = updateWarehousePort;
        this.updateTruckPort = updateTruckPort;
        this.loadWarehousePort = loadWarehousePort;
        this.loadTruckPort = loadTruckPort;
        this.loadWeighbridgePort = loadWeighbridgePort;
    }

    @Override
    public WarehouseId receiveWarehouseNumber(ReceiveWarehouseNumberCommand command) {
        // Load the truck by license plate
        Truck truck = loadTruckPort.loadTruck(command.licensePlate())
                .orElseThrow(() -> new IllegalArgumentException("Truck not found with license plate: " + command.licensePlate()));

        // Load the weighbridge by weighbridge number
        Weighbridge weighbridge = loadWeighbridgePort.loadWeighbridge(command.weighbridgeNumber())
                .orElseThrow(() -> new IllegalArgumentException("Weighbridge not found with number: " + command.weighbridgeNumber()));

        // Load the warehouse by sellerId, materialType, and timestamp
        Warehouse warehouse = loadWarehousePort.loadWarehouse(
                        truck.getLicensePlate().getSellerId(), // Adjust this as per your method to get SellerId
                        truck.getMaterialType(),
                        LocalDateTime.now())  // Assuming current timestamp; adjust as needed
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for truck with given parameters."));

        // Business logic to assign warehouse
        // For example, update warehouse with payload weight
        if (!warehouse.canStore(command.payloadWeight())) {
            throw new IllegalStateException("Warehouse has exceeded its maximum capacity.");
        }

        warehouse.store(command.payloadWeight());

        // Save updated state back to the repositories
        updateTruckPort.updateTruck(truck);
        updateWeighbridgePort.updateWeighbridge(weighbridge);
        updateWarehousePort.updateWarehouse(warehouse);

        return warehouse.getWarehouseId();
    }
}
