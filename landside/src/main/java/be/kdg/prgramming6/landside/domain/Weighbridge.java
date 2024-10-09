package be.kdg.prgramming6.landside.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Weighbridge {
    private final WeighbridgeNumber weighbridgeNumber;
    private Optional<Truck> currentTruck;

    public Weighbridge(WeighbridgeNumber weighbridgeNumber) {
        this.weighbridgeNumber = Objects.requireNonNull(weighbridgeNumber, "Weighbridge number cannot be null");
        this.currentTruck = Optional.empty();
    }

    // Method to process truck and assign warehouse
    public Optional<WarehouseId> processTruck(Truck truck, List<Warehouse> warehouses) {
        Objects.requireNonNull(truck, "Truck cannot be null");
        Objects.requireNonNull(warehouses, "Warehouses list cannot be null");

        updateWithTruck(truck);

        // Business logic to find the appropriate warehouse
        return warehouses.stream()
                .filter(warehouse -> warehouse.canStore(truck.getPayloadWeight()))
                .findFirst()
                .map(Warehouse::getWarehouseId);
    }

    public WeighbridgeNumber getWeighbridgeNumber() {
        return weighbridgeNumber;
    }

    public void updateWithTruck(Truck truck) {
        Objects.requireNonNull(truck, "Truck cannot be null");
        this.currentTruck = Optional.of(truck);
        logTruckProcessing(truck);
    }

    public Optional<Truck> getCurrentTruck() {
        return currentTruck;
    }

    private void logTruckProcessing(Truck truck) {
        System.out.println("Weighbridge " + weighbridgeNumber + " is processing truck: " + truck.getLicensePlate());
    }
}
