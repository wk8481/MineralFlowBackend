package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

@FunctionalInterface
public interface UpdatePurchaseOrderPort {
    void updatePurchaseOrder(PurchaseOrder purchaseOrder);
}