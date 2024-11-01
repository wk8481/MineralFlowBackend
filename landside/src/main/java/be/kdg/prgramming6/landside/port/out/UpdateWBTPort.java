package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;

@FunctionalInterface
public interface UpdateWBTPort {
    void update(WeighbridgeTicket weighbridgeTicket, WarehouseId warehouseId);
}