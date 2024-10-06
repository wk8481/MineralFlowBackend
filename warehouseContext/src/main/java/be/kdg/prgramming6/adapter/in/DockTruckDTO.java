package be.kdg.prgramming6.adapter.in;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class DockTruckDTO {
    private final String licensePlate;
    private final String materialType;
    private final UUID warehouseId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime deliveryDate;

    private final String dockNumber;
    private final UUID sellerId; // New field for SellerId

    // Constructor
    public DockTruckDTO(String licensePlate, String materialType, UUID warehouseId,
                        LocalDateTime deliveryDate, String dockNumber, UUID sellerId) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.warehouseId = warehouseId;
        this.deliveryDate = deliveryDate;
        this.dockNumber = dockNumber;
        this.sellerId = sellerId; // Assigning SellerId
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
        return sellerId; // Getter for SellerId
    }
}
