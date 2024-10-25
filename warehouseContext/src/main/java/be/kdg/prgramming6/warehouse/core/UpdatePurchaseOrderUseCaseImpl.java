package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrderStatus;
import be.kdg.prgramming6.warehouse.domain.Warehouse;
import be.kdg.prgramming6.warehouse.domain.WarehouseActivity;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;
import be.kdg.prgramming6.warehouse.port.in.UpdatePurchaseOrderUseCase;
import be.kdg.prgramming6.warehouse.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdatePurchaseOrderUseCaseImpl implements UpdatePurchaseOrderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdatePurchaseOrderUseCaseImpl.class);

    private final LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort;
    private final SavePurchaseOrderPort savePurchaseOrderPort;
    private final UpdatePurchaseOrderPort updatePurchaseOrderPort;
    private final LoadWarehouseByIdPort loadWarehouseByIdPort;
    private final List<UpdateWarehousePort> updateWarehousePorts;

    public UpdatePurchaseOrderUseCaseImpl(LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort, SavePurchaseOrderPort savePurchaseOrderPort, UpdatePurchaseOrderPort updatePurchaseOrderPort, LoadWarehouseByIdPort loadWarehouseByIdPort, List<UpdateWarehousePort> updateWarehousePorts) {
        this.loadPurchaseOrderByIdPort = loadPurchaseOrderByIdPort;
        this.savePurchaseOrderPort = savePurchaseOrderPort;
        this.updatePurchaseOrderPort = updatePurchaseOrderPort;
        this.loadWarehouseByIdPort = loadWarehouseByIdPort;
        this.updateWarehousePorts = updateWarehousePorts;
    }

    @Override
    @Transactional
    public void handle(PurchaseOrder receivedOrder) {
        Optional<PurchaseOrder> existingOrderOpt = loadPurchaseOrderByIdPort.loadPurchaseOrderById(receivedOrder.getPoNumber());

        if (existingOrderOpt.isPresent()) {
            PurchaseOrder existingOrder = existingOrderOpt.get();
            existingOrder.updateStatus(PurchaseOrderStatus.FULFILLED);
            updatePurchaseOrderPort.updatePurchaseOrder(existingOrder);

            // Hardcoded warehouseId
            WarehouseId warehouseId = new WarehouseId(UUID.fromString("e2f7bcdc-69da-4b0e-b73f-6adf79e8d5f9"));
            Optional<Warehouse> optionalWarehouse = loadWarehouseByIdPort.loadWarehouseById(warehouseId);
            Warehouse warehouse = optionalWarehouse.orElseThrow(() -> new IllegalArgumentException("Warehouse not found for ID: " + warehouseId));

            final WarehouseActivity warehouseActivity = warehouse.unloadWarehouse(warehouse.getMaterialType(), BigDecimal.TEN);
            updateWarehousePorts.forEach(updateWarehousePort -> updateWarehousePort.activityCreated(warehouse, warehouseActivity));

        } else {
            savePurchaseOrderPort.savePurchaseOrder(receivedOrder);
        }
    }
}