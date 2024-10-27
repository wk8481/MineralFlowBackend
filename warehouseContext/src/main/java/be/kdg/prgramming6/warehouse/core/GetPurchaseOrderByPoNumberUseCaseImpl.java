package be.kdg.prgramming6.warehouse.service;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.port.in.GetPurchaseOrderByPoNumberUseCase;
import be.kdg.prgramming6.warehouse.port.out.LoadPurchaseOrderByIdPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GetPurchaseOrderByPoNumberUseCaseImpl implements GetPurchaseOrderByPoNumberUseCase {
    private final LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort;

    public GetPurchaseOrderByPoNumberUseCaseImpl(LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort) {
        this.loadPurchaseOrderByIdPort = loadPurchaseOrderByIdPort;
    }

    @Override
    @Transactional
    public PurchaseOrder getPurchaseOrderByPoNumber(String poNumber) {
        return loadPurchaseOrderByIdPort.loadPurchaseOrderById(poNumber)
                .orElseThrow(() -> new IllegalArgumentException("Purchase Order not found for ID: " + poNumber));
    }
}