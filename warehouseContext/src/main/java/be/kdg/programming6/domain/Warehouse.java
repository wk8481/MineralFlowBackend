package be.kdg.programming6.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Warehouse {
    private final WarehouseId id;
    private int currentCapacity;  // Current number of trucks docked
    private final BigDecimal maxCapacity; // Maximum capacity of the warehouse
    private final BigDecimal overflowFactor; // Factor to determine overflow capacity

    // Constructor
    public Warehouse(WarehouseId id, BigDecimal maxCapacity, BigDecimal overflowFactor) {
        this.id = id;
        this.maxCapacity = maxCapacity; // Ensure this is a BigDecimal
        this.currentCapacity = 0; // Initially, the warehouse is empty
        this.overflowFactor = overflowFactor; // E.g., new BigDecimal("1.1") for 110% overflow
    }

    public WarehouseId getId() {
        return id;
    }

    // Validation logic for docking a truck
    public boolean canDockTruck(Truck truck) {
        // Check if the warehouse is at maximum capacity with overflow
        BigDecimal maxAllowedCapacity = maxCapacity.multiply(overflowFactor);
        if (BigDecimal.valueOf(currentCapacity).compareTo(maxAllowedCapacity) >= 0) {
            return false; // Cannot dock if over the overflow capacity
        }

        // Implement material type restrictions (if any)
        // Placeholder logic: assuming all material types are accepted
        return true; // Placeholder logic
    }

    // Method to dock a truck, increments current capacity
    public void dockTruck(Truck truck) {
        if (canDockTruck(truck)) {
            currentCapacity++;
        } else {
            throw new IllegalStateException("Truck cannot be docked due to capacity or restrictions.");
        }
    }

    // Method to generate the PDT
    public PayloadDeliveryTicket generatePDT(Truck truck, String conveyorBeltId, String weighingBridgeNumber, LocalDateTime deliveryDate) {
        // Create and return the PDT object
        return new PayloadDeliveryTicket(truck.licensePlate(), truck.materialType(), this.id, conveyorBeltId, weighingBridgeNumber, deliveryDate);
    }

    // Method to remove a truck after docking, decrements current capacity
    public void undockTruck() {
        if (currentCapacity > 0) {
            currentCapacity--;
        } else {
            throw new IllegalStateException("No trucks to undock.");
        }
    }

    // Method to get current capacity
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    // Method to get max capacity with overflow
    public BigDecimal getMaxCapacityWithOverflow() {
        return maxCapacity.multiply(overflowFactor);
    }
}
