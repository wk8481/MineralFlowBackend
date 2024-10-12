package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.common.security.messages.SecuredMessage;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DockTruckDTO {
    private final String licensePlate;
    private final String materialType;
    private final UUID warehouseId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime deliveryDate;
    private final String dockNumber;
    private final UUID sellerId; // New field for sellerId
    private final BigDecimal weight;

    // Constructor
    public DockTruckDTO(String licensePlate, String materialType, UUID warehouseId,
                        LocalDateTime deliveryDate, String dockNumber, UUID sellerId, BigDecimal weight) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.warehouseId = warehouseId;
        this.deliveryDate = deliveryDate;
        this.dockNumber = dockNumber;
        this.sellerId = sellerId; // Assigning sellerId
        this.weight = weight;
    }

    // Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    public UUID getSellerId() {
        return sellerId; // Getter for sellerId
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
