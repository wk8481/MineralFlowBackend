package be.kdg.programming6.domain;

import java.time.LocalDateTime;

public class PayloadDeliveryTicket {
    private final LicensePlate licensePlate;
    private final MaterialType materialType;
    private final WarehouseId warehouseId;
    private final String conveyorBeltId;
    private final String weighingBridgeNumber;
    private final LocalDateTime deliveryDate;

    public PayloadDeliveryTicket(final LicensePlate licensePlate, final MaterialType materialType, final WarehouseId warehouseId, final String conveyorBeltId, String weighingBridgeNumber, final LocalDateTime deliveryDate) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.warehouseId = warehouseId;
        this.conveyorBeltId = conveyorBeltId;
        this.weighingBridgeNumber = weighingBridgeNumber;
        this.deliveryDate = deliveryDate;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getConveyorBeltId() {
        return conveyorBeltId;
    }

    public String getWeighingBridgeNumber() {
        return weighingBridgeNumber;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
}
