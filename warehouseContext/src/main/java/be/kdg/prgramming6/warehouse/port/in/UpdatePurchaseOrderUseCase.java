package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;



public interface UpdatePurchaseOrderUseCase {
    void handle(PurchaseOrder receivedOrder);
}