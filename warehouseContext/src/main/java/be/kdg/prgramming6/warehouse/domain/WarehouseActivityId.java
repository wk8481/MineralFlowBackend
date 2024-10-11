package be.kdg.prgramming6.warehouse.domain;

import java.util.UUID;

public record WarehouseActivityId(WarehouseId warehouseId, UUID activityId) {

    public WarehouseActivityId {
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse ID cannot be null");
        }
        if (activityId == null) {
            throw new IllegalArgumentException("Activity ID cannot be null");
        }
    }
}
