package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.domain.WarehouseId;

import java.util.Optional;

@FunctionalInterface
public interface LoadWarehousePort {
    Optional<Warehouse> loadWarehouse(WarehouseId warehouseId);
    // Additional load methods based on different attributes can be added here
}
