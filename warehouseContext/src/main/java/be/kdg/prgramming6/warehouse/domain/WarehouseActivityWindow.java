package be.kdg.prgramming6.warehouse.domain;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class WarehouseActivityWindow {
    private final WarehouseId warehouseId;
    private final List<WarehouseActivity> activities;

    public WarehouseActivityWindow(WarehouseId warehouseId, List<WarehouseActivity> activities) {
        this.warehouseId = warehouseId;
        this.activities = activities;
    }

    WarehouseActivity addActivity(final WarehouseActivityType type, SellerId sellerId, MaterialType materialType, BigDecimal weight, FulfillmentStatus fulfillmentStatus) {
        final WarehouseActivityId activityId = new WarehouseActivityId(warehouseId, UUID.randomUUID());
        final WarehouseActivity activity = new WarehouseActivity(
                activityId,
                LocalDateTime.now(),
                type,
                sellerId,
                materialType,
                weight,
                fulfillmentStatus // Use the provided fulfillment status
        );
        activities.add(activity);
        return activity;
    }

    List<WarehouseActivity> getActivities() {
        return Collections.unmodifiableList(activities);
    }
}