package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.Warehouse;
import be.kdg.prgramming6.warehouse.domain.WarehouseActivity;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;

@FunctionalInterface
public interface UpdateWarehousePort {
    void activityCreated(Warehouse warehouse, WarehouseActivity activity);
}
