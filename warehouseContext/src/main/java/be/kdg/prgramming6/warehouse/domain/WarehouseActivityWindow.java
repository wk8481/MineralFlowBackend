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

    public Capacity calculateCapacity() {
        final LocalDateTime time = activities.stream()
            .max(Comparator.comparing(WarehouseActivity::time))
            .orElseThrow()
            .time();
        final BigDecimal weight = activities.stream()
                .map(WarehouseActivity::getChangeToCapacity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Capacity(time, weight);
    }

    WarehouseActivity addActivity(final WarehouseActivityType type, SellerId sellerId, MaterialType materialType, BigDecimal weight) {
        final WarehouseActivityId activityId = new WarehouseActivityId(warehouseId, UUID.randomUUID());
        final WarehouseActivity activity = new WarehouseActivity(
            activityId,
            LocalDateTime.now(),
            type,
            sellerId,
            materialType,
            weight
        );
        activities.add(activity);
        return activity;
    }

    List<WarehouseActivity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

}
