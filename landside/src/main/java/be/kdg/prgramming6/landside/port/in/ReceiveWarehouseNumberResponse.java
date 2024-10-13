package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.domain.WeighbridgeNumber;

public class ReceiveWarehouseNumberResponse {
    private final WarehouseId warehouseId;
    private final WeighbridgeNumber weighbridgeNumber;

    public ReceiveWarehouseNumberResponse(WarehouseId warehouseId, WeighbridgeNumber weighbridgeNumber) {
        this.warehouseId = warehouseId;
        this.weighbridgeNumber = weighbridgeNumber;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public WeighbridgeNumber getWeighbridgeNumber() {
        return weighbridgeNumber;
    }
}