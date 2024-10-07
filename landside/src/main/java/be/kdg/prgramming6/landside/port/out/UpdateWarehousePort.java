package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Warehouse;

@FunctionalInterface
public interface UpdateWarehousePort {
    void updateWarehouse(Warehouse warehouse);
}
