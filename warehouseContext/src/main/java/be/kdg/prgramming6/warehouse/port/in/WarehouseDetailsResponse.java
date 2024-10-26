package be.kdg.prgramming6.warehouse.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public class WarehouseDetailsResponse {
    private final UUID warehouseId;
    private final UUID sellerId;
    private final String materialType;
    private final BigDecimal totalMaterial;

    public WarehouseDetailsResponse(UUID warehouseId, UUID sellerId, String materialType, BigDecimal totalMaterial) {
        this.warehouseId = warehouseId;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.totalMaterial = totalMaterial;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public BigDecimal getTotalMaterial() {
        return totalMaterial;
    }
}