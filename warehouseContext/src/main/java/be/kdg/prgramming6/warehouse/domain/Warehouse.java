package be.kdg.prgramming6.warehouse.domain;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final WarehouseId warehouseId;
    private final List<Truck> dockedTrucks; // Changed to store Truck objects
    private final SellerId sellerId;
    private final MaterialType materialType;
    private final WarehouseActivityWindow activities;




    // Constructor
    public Warehouse(WarehouseId warehouseId, SellerId sellerId, MaterialType materialType, WarehouseActivityWindow activities) {
        this.warehouseId = warehouseId;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.activities = activities;
        this.dockedTrucks = new ArrayList<>(); // Initialize the list for docked trucks
    }

    public Warehouse(final WarehouseId warehouseId, final WarehouseActivityWindow activities) {
        this.warehouseId = warehouseId;
        this.activities = activities;
        this.dockedTrucks = new ArrayList<>();
        this.sellerId = null;
        this.materialType = null;


    }

    public Capacity calculateCapacity() {
        return activities.calculateCapacity();
    }

    public WarehouseActivity loadWarehouse(final BigDecimal weight) {
        return activities.addActivity(WarehouseActivityType.DELIVERY, sellerId, materialType, weight);
    }

    public List<WarehouseActivity> getActivities() {
        return activities.getActivities();
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

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public SellerId getSellerId() {
        return sellerId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }


}
