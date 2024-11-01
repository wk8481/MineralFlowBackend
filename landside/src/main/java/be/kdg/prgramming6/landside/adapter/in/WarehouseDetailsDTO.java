package be.kdg.prgramming6.landside.adapter.in;

import java.math.BigDecimal;
import java.util.UUID;

public class WarehouseDetailsDTO {
    private UUID warehouseId;
    private UUID sellerId;
    private String materialType;
    private BigDecimal currentCapacity;

    // Getters and setters
    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(BigDecimal currentCapacity) {
        this.currentCapacity = currentCapacity;
    }



}