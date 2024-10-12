package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;
import be.kdg.prgramming6.landside.domain.MaterialType;
import be.kdg.prgramming6.landside.domain.SellerId;
import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.port.in.WarehouseProjector;
import be.kdg.prgramming6.landside.port.out.LoadWarehousePort;
import be.kdg.prgramming6.landside.port.out.UpdateWarehousePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class WarehouseProjectorImpl implements WarehouseProjector {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseProjectorImpl.class);

    private final LoadWarehousePort loadWarehousePort;
    private final UpdateWarehousePort updateWarehousePort;

    public WarehouseProjectorImpl(LoadWarehousePort loadWarehousePort, UpdateWarehousePort updateWarehousePort) {
        this.loadWarehousePort = loadWarehousePort;
        this.updateWarehousePort = updateWarehousePort;
    }





    @Override
    public void projectWarehouse(UUID warehouseId, WarehouseActivityType warehouseActivityType, UUID sellerId, String materialType, LocalDateTime timestamp, BigDecimal weight) {
        final Warehouse warehouse = loadWarehousePort.loadWarehouse(new SellerId(sellerId), MaterialType.valueOf(materialType), timestamp)                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for given parameters."));
        warehouse.modifyCapacity(warehouseActivityType, weight);
        LOGGER.info("Updating warehouse {} at landside with currentCapacity of {}", warehouseId, warehouse.getCurrentCapacity());
        updateWarehousePort.updateWarehouse(warehouse);

    }
}
