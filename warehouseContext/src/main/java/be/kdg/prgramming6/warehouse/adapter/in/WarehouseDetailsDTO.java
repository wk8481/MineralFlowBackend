// warehouseContext/src/main/java/be/kdg/prgramming6/warehouse/adapter/in/web/dto/WarehouseDetailsDto.java
package be.kdg.prgramming6.warehouse.adapter.in;

import java.math.BigDecimal;
import java.util.UUID;

public class WarehouseDetailsDTO {
    private UUID warehouseId;
    private UUID sellerId;
    private String materialType;
    private BigDecimal totalMaterial;

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

    public BigDecimal getTotalMaterial() {
        return totalMaterial;
    }

    public void setTotalMaterial(BigDecimal totalMaterial) {
        this.totalMaterial = totalMaterial;
    }
}