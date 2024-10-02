package be.kdg.programming6.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final WarehouseId warehouseId;
    private final List<Truck> dockedTrucks; // Changed to store Truck objects
    private final SellerId sellerId;

    // Constructor
    public Warehouse(WarehouseId warehouseId, SellerId sellerId) {
        this.warehouseId = warehouseId;
        this.sellerId = sellerId;
        this.dockedTrucks = new ArrayList<>(); // Initialize the list for docked trucks
    }

    public WarehouseId getId() {
        return warehouseId;
    }

    // Method to dock a truck, adds the truck to the docked trucks list
    public void dockTruck(Truck truck) {
        dockedTrucks.add(truck); // Log the truck object
        // Additional logic can be added here if needed
    }

    // Method to assign a weighbridge number using WeighbridgeNumber record
    public WeighbridgeNumber assignWeighbridgeNumber(Truck truck) {
        return WeighbridgeNumber.generate(truck);
    }

    // Method to generate the PDT, now using dock number instead of conveyor belt ID
    public PayloadDeliveryTicket generatePDT(Truck truck, String dockNumber, LocalDateTime deliveryDate) {
        return new PayloadDeliveryTicket(
                truck.getLicensePlate(),
                truck.getMaterialType(),
                deliveryDate,
                warehouseId,
                dockNumber
        );
    }

    // Optional: Method to get all docked trucks for inspection or processing
    public List<Truck> getDockedTrucks() {
        return new ArrayList<>(dockedTrucks); // Return a copy to maintain encapsulation
    }
}
