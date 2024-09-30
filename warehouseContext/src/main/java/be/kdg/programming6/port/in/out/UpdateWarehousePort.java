package be.kdg.programming6.port.in.out;

import be.kdg.programming6.domain.WarehouseId;

public interface UpdateWarehousePort {
    void updateWarehouse(WarehouseId warehouseId, String weighbridgeNumber, String pdt);
}
