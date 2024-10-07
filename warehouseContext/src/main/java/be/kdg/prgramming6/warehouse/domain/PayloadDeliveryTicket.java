package be.kdg.prgramming6.warehouse.domain;

import java.time.LocalDateTime;

public class PayloadDeliveryTicket {
    private final LicensePlate licensePlate; // License plate of the truck
    private final MaterialType materialType; // Type of material
    private final LocalDateTime deliveryDate; // Date of delivery
    private final WarehouseId warehouseId; // Warehouse ID
    private final String dockNumber; // Dock number

    // Constructor
    public PayloadDeliveryTicket(final LicensePlate licensePlate, final MaterialType materialType, final LocalDateTime deliveryDate, final WarehouseId warehouseId, final String dockNumber) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.deliveryDate = deliveryDate;
        this.warehouseId = warehouseId;
        this.dockNumber = dockNumber;
    }

    // Getters
    public MaterialType getMaterialType() {
        return materialType;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "Payload Delivery Ticket {" +
                "Material Type: " + materialType +
                ", Delivery Date: " + deliveryDate +
                ", Warehouse ID: " + warehouseId +
                ", Dock Number: '" + dockNumber + '\'' +
                '}';
    }
}
