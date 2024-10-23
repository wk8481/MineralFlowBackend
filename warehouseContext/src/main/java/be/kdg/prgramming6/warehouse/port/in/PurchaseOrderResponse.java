package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

import java.util.Optional;

public class PurchaseOrderResponse {
    private final Optional<PurchaseOrder> purchaseOrder;
    private final boolean updated;

    public PurchaseOrderResponse(Optional<PurchaseOrder> purchaseOrder, boolean updated) {
        this.purchaseOrder = purchaseOrder;
        this.updated = updated;
    }

    public Optional<PurchaseOrder> getPurchaseOrder() {
        return purchaseOrder;
    }

    public boolean isUpdated() {
        return updated;
    }
}