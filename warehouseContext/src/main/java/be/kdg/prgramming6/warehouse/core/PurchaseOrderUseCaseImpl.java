package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrderStatus;
import be.kdg.prgramming6.warehouse.port.in.PurchaseOrderUseCase;
import be.kdg.prgramming6.warehouse.port.out.LoadAllPurchaseOrdersPort;
import be.kdg.prgramming6.warehouse.port.out.LoadPurchaseOrderByIdPort;
import be.kdg.prgramming6.warehouse.port.out.SavePurchaseOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderUseCaseImpl implements PurchaseOrderUseCase {
    private final LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort;
    private final SavePurchaseOrderPort savePurchaseOrderPort;
    private final LoadAllPurchaseOrdersPort loadAllPurchaseOrdersPort;

    public PurchaseOrderUseCaseImpl(LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort, SavePurchaseOrderPort savePurchaseOrderPort, LoadAllPurchaseOrdersPort loadAllPurchaseOrdersPort) {
        this.loadPurchaseOrderByIdPort = loadPurchaseOrderByIdPort;
        this.savePurchaseOrderPort = savePurchaseOrderPort;
        this.loadAllPurchaseOrdersPort = loadAllPurchaseOrdersPort;
    }

    @Override
    @Transactional
    public void handle(PurchaseOrder receivedOrder) {
        Optional<PurchaseOrder> existingOrderOpt = loadPurchaseOrderByIdPort.loadPurchaseOrderById(receivedOrder.getPoNumber());

        if (existingOrderOpt.isPresent()) {
            PurchaseOrder existingOrder = existingOrderOpt.get();
            if (existingOrder.matches(receivedOrder)) {
                existingOrder.updateStatus(PurchaseOrderStatus.FULFILLED);
                savePurchaseOrderPort.savePurchaseOrder(existingOrder);
            }
        } else {
            savePurchaseOrderPort.savePurchaseOrder(receivedOrder);
        }
    }

    @Override
    @Transactional
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return loadAllPurchaseOrdersPort.loadAllPurchaseOrders();
    }
}