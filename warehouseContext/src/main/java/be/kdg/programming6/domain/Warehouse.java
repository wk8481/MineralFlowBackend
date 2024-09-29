package be.kdg.programming6.domain;

import be.kdg.programming6.domain.CustomerId;
import be.kdg.programming6.domain.PayloadDeliveryTicket;
import be.kdg.programming6.domain.Truck;
import be.kdg.programming6.domain.WarehouseId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final WarehouseId warehouseId;
    private final int maxTrucksPerHour;
    private final List<LocalDateTime> dockedTrucks;
    private final CustomerId customerId;

    // Constructor
    public Warehouse(WarehouseId warehouseId, int maxTrucksPerHour, CustomerId customerId, CustomerId customerId1) {
        this.warehouseId = warehouseId;
        this.maxTrucksPerHour = maxTrucksPerHour;
        this.customerId = customerId1;
        this.dockedTrucks = new ArrayList<>();
    }

    public WarehouseId getId() {
        return warehouseId;
    }

    // Validation logic for docking a truck
    public boolean canDockTruck() {
        // Remove docked trucks older than an hour
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        dockedTrucks.removeIf(timestamp -> timestamp.isBefore(oneHourAgo));

        // Check if the count of docked trucks in the last hour is less than the maximum
        return dockedTrucks.size() < maxTrucksPerHour;
    }

    // Method to dock a truck, increments docked truck count
    public void dockTruck(Truck truck) {
        if (canDockTruck()) {
            dockedTrucks.add(LocalDateTime.now());
        } else {
            throw new IllegalStateException("Truck cannot be docked due to capacity or restrictions.");
        }
    }

    // Method to assign a weighbridge number using WeighbridgeNumber record
    public WeighbridgeNumber assignWeighbridgeNumber(Truck truck) {
        return WeighbridgeNumber.generate(truck);
    }

    // Method to generate the PDT, now using dock number instead of conveyor belt ID
    public PayloadDeliveryTicket generatePDT(Truck truck, String dockNumber, LocalDateTime deliveryDate) {
        return new PayloadDeliveryTicket(truck.getMaterialType(), deliveryDate, this.getId(), dockNumber);
    }

    // Method to remove a truck after docking (not currently needed but kept for future use)
    public void undockTruck() {
        // You can implement logic here if needed in the future
    }

    public int getMaxTrucksPerHour() {
        return maxTrucksPerHour;
    }
}
