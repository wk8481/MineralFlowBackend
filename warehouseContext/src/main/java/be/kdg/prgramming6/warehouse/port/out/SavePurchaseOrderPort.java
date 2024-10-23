package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

@FunctionalInterface
public interface SavePurchaseOrderPort {
    void savePurchaseOrder(PurchaseOrder purchaseOrder);
}