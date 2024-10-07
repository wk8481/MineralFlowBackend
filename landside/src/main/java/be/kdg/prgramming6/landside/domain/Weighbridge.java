package be.kdg.prgramming6.landside.domain;

import java.util.List;
import java.util.Objects;

public class Weighbridge {
    private final WeighbridgeNumber weighbridgeNumber;
    private Truck currentTruck;  // To store the current truck being processed

    public Weighbridge(WeighbridgeNumber weighbridgeNumber) {
        this.weighbridgeNumber = Objects.requireNonNull(weighbridgeNumber, "Weighbridge number cannot be null");
    }

    public WarehouseId processTruck(Truck truck, List<Warehouse> warehouses) {
        // Delegate the warehouse assignment to the domain object (Warehouse)
        for (Warehouse warehouse : warehouses) {
            if (warehouse.canStore(truck.getPayloadWeight())) {
                warehouse.store(truck.getPayloadWeight());  // Store the payload in the warehouse
                return warehouse.getWarehouseId();
            }
        }
        throw new IllegalStateException("No warehouse found with enough capacity for the payload.");
    }

    public WeighbridgeNumber getWeighbridgeNumber() {
        return weighbridgeNumber;
    }

    public void updateWithTruck(Truck truck) {
        // Store the current truck details
        this.currentTruck = truck;

        // You can also add additional logic, like logging or processing the payload
        System.out.println("Weighbridge " + weighbridgeNumber + " is processing truck: " + truck.getLicensePlate());

        // If needed, you could also implement more functionality like:
        // - Recording the weight of the truck
        // - Updating the weighbridge status
    }

    public Truck getCurrentTruck() {
        return currentTruck; // Optional: to retrieve the current truck if needed
    }
}
