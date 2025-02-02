package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.domain.WarehouseId;

@FunctionalInterface
public interface GetWarehouseByIdUseCase {
    Warehouse getWarehouseById(WarehouseId warehouseId);
}
