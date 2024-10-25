package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

public interface UpdatePurchaseOrderPort {
    void updatePurchaseOrder(PurchaseOrder purchaseOrder);
}