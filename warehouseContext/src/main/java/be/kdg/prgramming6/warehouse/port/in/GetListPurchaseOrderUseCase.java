package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

import java.util.List;

@FunctionalInterface
public interface GetListPurchaseOrderUseCase {
    List<PurchaseOrder> getAllPurchaseOrders();
}
