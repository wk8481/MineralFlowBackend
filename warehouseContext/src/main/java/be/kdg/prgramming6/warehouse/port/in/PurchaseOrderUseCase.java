package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderUseCase {
    void handle(PurchaseOrder receivedOrder);
    List<PurchaseOrder> getAllPurchaseOrders();
}