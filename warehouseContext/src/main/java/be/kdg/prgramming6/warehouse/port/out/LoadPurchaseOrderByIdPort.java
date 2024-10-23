package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

import java.util.Optional;

@FunctionalInterface
public interface LoadPurchaseOrderByIdPort {
    Optional<PurchaseOrder> loadPurchaseOrderById(String poNumber);
}