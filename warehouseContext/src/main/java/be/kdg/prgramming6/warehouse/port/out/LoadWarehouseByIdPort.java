package be.kdg.prgramming6.warehouse.port.out;

import java.util.Optional;

import be.kdg.prgramming6.warehouse.domain.Warehouse;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;

@FunctionalInterface
public interface LoadWarehouseByIdPort {
    Optional<Warehouse> loadWarehouseById(WarehouseId warehouseId);
}
