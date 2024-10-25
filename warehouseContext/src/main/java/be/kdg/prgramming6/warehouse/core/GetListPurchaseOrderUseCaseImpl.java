package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.port.in.GetListPurchaseOrderUseCase;
import be.kdg.prgramming6.warehouse.port.out.LoadAllPurchaseOrdersPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListPurchaseOrderUseCaseImpl implements GetListPurchaseOrderUseCase {
    private final LoadAllPurchaseOrdersPort loadAllPurchaseOrdersPort;

    public GetListPurchaseOrderUseCaseImpl(LoadAllPurchaseOrdersPort loadAllPurchaseOrdersPort) {
        this.loadAllPurchaseOrdersPort = loadAllPurchaseOrdersPort;
    }

    @Override
    @Transactional
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return loadAllPurchaseOrdersPort.loadAllPurchaseOrders();
    }
}