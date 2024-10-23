package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

import java.util.List;

@FunctionalInterface
public interface LoadAllPurchaseOrdersPort {
    List<PurchaseOrder> loadAllPurchaseOrders();
}