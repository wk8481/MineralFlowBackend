package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.WarehouseActivityId;

public interface UpdateFulfillmentPort {
    void updateFulfillmentStatus(WarehouseActivityId activityId, boolean isFulfilled);
}