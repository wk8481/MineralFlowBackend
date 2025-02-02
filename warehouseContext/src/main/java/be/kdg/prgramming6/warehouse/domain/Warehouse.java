package be.kdg.prgramming6.warehouse.domain;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Warehouse {
    private final WarehouseId warehouseId;
    private final List<Truck> dockedTrucks;
    private final SellerId sellerId;
    private final MaterialType materialType;
    private final WarehouseActivityWindow activities;

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

    public WarehouseActivity unloadWarehouse(MaterialType materialType, BigDecimal weight) {
        return activities.addActivity(WarehouseActivityType.SHIPMENT, sellerId, materialType, weight, FulfillmentStatus.NONE);
    }

    public void dockTruck(Truck truck) {
        dockedTrucks.add(truck);
    }

    public WeighbridgeNumber assignWeighbridgeNumber(Truck truck) {
        return WeighbridgeNumber.generate(truck);
    }

    public PayloadDeliveryTicket generatePDT(Truck truck, String dockNumber, LocalDateTime deliveryDate) {
        return new PayloadDeliveryTicket(truck.getLicensePlate(), truck.getMaterialType(), deliveryDate, warehouseId, dockNumber);
    }

    public void updatePDT(PayloadDeliveryTicket pdt, String dockNumber, LocalDateTime deliveryDate) {
        pdt.update(dockNumber, deliveryDate);
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

    public List<WarehouseActivity> getActivities() {
        return activities.getActivities();
    }
    

}