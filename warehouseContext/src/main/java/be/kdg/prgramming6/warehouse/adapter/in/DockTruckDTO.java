package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.common.security.messages.SecuredMessage;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DockTruckDTO {
    @NotBlank(message = "License plate is mandatory")
    private final String licensePlate;

    @NotBlank(message = "Material type is mandatory")
    private final String materialType;

    @NotNull(message = "Warehouse ID is mandatory")
    private final UUID warehouseId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Delivery date is mandatory")
    private final LocalDateTime deliveryDate;

    @NotBlank(message = "Dock number is mandatory")
    private final String dockNumber;

    @NotNull(message = "Seller ID is mandatory")
    private final UUID sellerId;

    @NotNull(message = "Weight is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than zero")
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
