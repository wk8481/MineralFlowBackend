package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;

public interface GetPurchaseOrderByPoNumberUseCase {
    PurchaseOrder getPurchaseOrderByPoNumber(String poNumber);
}