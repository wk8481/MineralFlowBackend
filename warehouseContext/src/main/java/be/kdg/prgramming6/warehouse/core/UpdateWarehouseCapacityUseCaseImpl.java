package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.UpdateWarehouseCapacityCommand;
import be.kdg.prgramming6.warehouse.port.in.UpdateWarehouseCapacityUseCase;
import be.kdg.prgramming6.warehouse.port.in.out.LoadWarehouseByIdPort;
import be.kdg.prgramming6.warehouse.port.in.out.UpdateWarehousePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UpdateWarehouseCapacityUseCaseImpl implements UpdateWarehouseCapacityUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateWarehouseCapacityUseCaseImpl.class);

    private final LoadWarehouseByIdPort loadWarehouseByIdPort;
    private final List<UpdateWarehousePort> updateWarehousePorts;

    public UpdateWarehouseCapacityUseCaseImpl(
            LoadWarehouseByIdPort loadWarehouseByIdPort,
            List<UpdateWarehousePort> updateWarehousePorts) {
        this.loadWarehouseByIdPort = loadWarehouseByIdPort;
        this.updateWarehousePorts = updateWarehousePorts;
    }

    @Override
    public void updateWarehouseCapacity(UpdateWarehouseCapacityCommand command) {
        WarehouseId warehouseId = command.warehouseId();
        BigDecimal weight = command.weight();

        logger.debug("Updating warehouse capacity for Warehouse ID: {} with weight: {}", warehouseId, weight);

        Optional<Warehouse> optionalWarehouse = loadWarehouseByIdPort.loadWarehouseById(warehouseId);
        Warehouse warehouse = optionalWarehouse.orElseThrow(() -> new IllegalArgumentException("Warehouse not found for ID: " + warehouseId));

        // Add the activity with the given weight
        final WarehouseActivity warehouseActivity = warehouse.addActivity(weight);
        updateWarehousePorts.forEach(updateWarehousePort -> updateWarehousePort.activityCreated(warehouse, warehouseActivity));
    }
}