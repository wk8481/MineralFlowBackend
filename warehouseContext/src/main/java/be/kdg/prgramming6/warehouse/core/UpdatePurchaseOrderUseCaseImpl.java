package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.UpdatePurchaseOrderUseCase;
import be.kdg.prgramming6.warehouse.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UpdatePurchaseOrderUseCaseImpl implements UpdatePurchaseOrderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdatePurchaseOrderUseCaseImpl.class);

    private final LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort;
    private final SavePurchaseOrderPort savePurchaseOrderPort;
    private final UpdatePurchaseOrderPort updatePurchaseOrderPort;
    private final LoadWarehouseByIdPort loadWarehouseByIdPort;
    private final List<UpdateWarehousePort> updateWarehousePorts;
    private final LoadWarehousesByMaterialTypeAndStatusPort loadWarehousesByMaterialTypeAndStatusPort;
    private final UpdateFulfillmentPort updateFulfillmentPort;

    public UpdatePurchaseOrderUseCaseImpl(LoadPurchaseOrderByIdPort loadPurchaseOrderByIdPort, SavePurchaseOrderPort savePurchaseOrderPort, UpdatePurchaseOrderPort updatePurchaseOrderPort, LoadWarehouseByIdPort loadWarehouseByIdPort, List<UpdateWarehousePort> updateWarehousePorts, LoadWarehousesByMaterialTypeAndStatusPort loadWarehousesByMaterialTypeAndStatusPort, UpdateFulfillmentPort updateFulfillmentPort) {
        this.loadPurchaseOrderByIdPort = loadPurchaseOrderByIdPort;
        this.savePurchaseOrderPort = savePurchaseOrderPort;
        this.updatePurchaseOrderPort = updatePurchaseOrderPort;
        this.loadWarehouseByIdPort = loadWarehouseByIdPort;
        this.updateWarehousePorts = updateWarehousePorts;
        this.loadWarehousesByMaterialTypeAndStatusPort = loadWarehousesByMaterialTypeAndStatusPort;
        this.updateFulfillmentPort = updateFulfillmentPort;
    }

    @Override
    @Transactional
    public void handle(PurchaseOrder receivedOrder) {
        logger.info("Handling purchase order: {}", receivedOrder.getPoNumber());
        Optional<PurchaseOrder> existingOrderOpt = loadPurchaseOrderByIdPort.loadPurchaseOrderById(receivedOrder.getPoNumber());

        if (existingOrderOpt.isPresent()) {
            logger.info("Existing order found: {}", receivedOrder.getPoNumber());
            PurchaseOrder existingOrder = existingOrderOpt.get();
            existingOrder.updateStatus(PurchaseOrderStatus.FULFILLED);
            updatePurchaseOrderPort.updatePurchaseOrder(existingOrder);
            logger.info("Updated status of existing order to FULFILLED: {}", existingOrder.getPoNumber());

            for (OrderLine orderLine : receivedOrder.getOrderLines()) {
                logger.info("Processing order line: {}", orderLine);
                List<WarehouseActivity> activities = loadWarehousesByMaterialTypeAndStatusPort.loadAllDeliveriesByMaterialTypeAndStatus(orderLine.getMaterialType(), FulfillmentStatus.OUTSTANDING.name());
                activities.stream()
                        .min(Comparator.comparing(WarehouseActivity::time))
                        .ifPresent(oldestActivity -> {
                            WarehouseId warehouseId = oldestActivity.activityId().warehouseId();
                            logger.info("Found oldest activity for material type {}: {}", orderLine.getMaterialType(), oldestActivity);
                            Optional<Warehouse> optionalWarehouse = loadWarehouseByIdPort.loadWarehouseById(warehouseId);
                            Warehouse warehouse = optionalWarehouse.orElseThrow(() -> new IllegalArgumentException("Warehouse not found for ID: " + warehouseId));

                            BigDecimal weight = BigDecimal.valueOf(orderLine.getAmountInTons());
                            final WarehouseActivity warehouseActivity = warehouse.unloadWarehouse(orderLine.getMaterialType(), weight);
                            updateWarehousePorts.forEach(updateWarehousePort -> updateWarehousePort.activityCreated(warehouse, warehouseActivity));
                            logger.info("Created new warehouse activity: {}", warehouseActivity);

                            // Update fulfillment status
                            updateFulfillmentPort.updateFulfillmentStatus(oldestActivity.activityId(), true);
                            logger.info("Updated fulfillment status for activity ID: {}", oldestActivity.activityId());
                        });
            }

        } else {
            logger.info("No existing order found, saving new order: {}", receivedOrder.getPoNumber());
            savePurchaseOrderPort.savePurchaseOrder(receivedOrder);
        }
    }
}