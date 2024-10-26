package be.kdg.prgramming6.landside.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public class WarehouseDetailsResponse {
    private final UUID warehouseId;
    private final UUID sellerId;
    private final String materialType;
    private final BigDecimal currentCapacity;

    public WarehouseDetailsResponse(UUID warehouseId, UUID sellerId, String materialType, BigDecimal currentCapacity) {
        this.warehouseId = warehouseId;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.currentCapacity = currentCapacity;
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

    public BigDecimal getCurrentCapacity() {
        return currentCapacity;
    }
}