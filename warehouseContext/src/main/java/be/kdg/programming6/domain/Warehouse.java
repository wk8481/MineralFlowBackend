package be.kdg.programming6.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private static final int MAX_TRUCKS_PER_HOUR = 40; // Hardcoded maxTrucksPerHour
    private final WarehouseId warehouseId;
    private final List<LocalDateTime> dockedTrucks;
    private final SellerId sellerId;

    // Constructor
    public Warehouse(WarehouseId warehouseId, SellerId sellerId) {
        this.warehouseId = warehouseId;
        this.sellerId = sellerId;
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
        return dockedTrucks.size() < MAX_TRUCKS_PER_HOUR;
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


    public int getMaxTrucksPerHour() {
        return MAX_TRUCKS_PER_HOUR;
    }
}
