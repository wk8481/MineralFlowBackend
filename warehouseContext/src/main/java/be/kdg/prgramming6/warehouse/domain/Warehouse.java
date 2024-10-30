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
    public Warehouse(final WarehouseId warehouseId, final SellerId sellerId, final MaterialType materialType, final WarehouseActivityWindow activities) {
        this.warehouseId = warehouseId;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.activities = activities;
        this.dockedTrucks = new ArrayList<>();
    }


    public WarehouseActivity loadWarehouse(final MaterialType materialType, final BigDecimal weight) {
        return activities.addActivity(WarehouseActivityType.DELIVERY, sellerId, materialType, weight, FulfillmentStatus.OUTSTANDING);
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

    public WarehouseActivity unloadWarehouse(MaterialType materialType, BigDecimal weight) {
        return activities.addActivity(WarehouseActivityType.SHIPMENT, sellerId, materialType, weight, FulfillmentStatus.NONE);
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


    //hmm

}





