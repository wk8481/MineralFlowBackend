package be.kdg.prgramming6.port.in.out;

import be.kdg.prgramming6.domain.WarehouseId;

public interface UpdateWarehousePort {
    void updateWarehouse(WarehouseId warehouseId, String weighbridgeNumber, String pdt);
}
