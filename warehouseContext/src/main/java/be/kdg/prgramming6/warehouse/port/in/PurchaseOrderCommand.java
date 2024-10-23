package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

public class PurchaseOrderCommand {
    private final PurchaseOrder receivedOrder;

    public PurchaseOrderCommand(PurchaseOrder receivedOrder) {
        this.receivedOrder = receivedOrder;
    }

    public PurchaseOrder getReceivedOrder() {
        return receivedOrder;
    }
}