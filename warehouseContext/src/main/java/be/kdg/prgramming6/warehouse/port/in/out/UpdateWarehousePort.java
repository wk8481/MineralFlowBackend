package be.kdg.prgramming6.warehouse.port.in.out;

import be.kdg.prgramming6.warehouse.domain.WarehouseId;

public interface UpdateWarehousePort {
    void updateWarehouse(WarehouseId warehouseId, String weighbridgeNumber, String pdt);
}
