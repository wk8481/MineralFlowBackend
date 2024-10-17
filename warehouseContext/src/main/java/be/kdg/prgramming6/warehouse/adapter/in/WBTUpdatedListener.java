package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.common.events.WBTUpdatedEvent;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;
import be.kdg.prgramming6.warehouse.port.in.UpdateWarehouseCapacityUseCase;
import be.kdg.prgramming6.warehouse.port.in.UpdateWarehouseCapacityCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WBTUpdatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WBTUpdatedListener.class);
    private static final String WBT_UPDATED_QUEUE = "wbt_updated";

    private final UpdateWarehouseCapacityUseCase updateWarehouseCapacityUseCase;

    public WBTUpdatedListener(UpdateWarehouseCapacityUseCase updateWarehouseCapacityUseCase) {
        this.updateWarehouseCapacityUseCase = updateWarehouseCapacityUseCase;
    }

    @RabbitListener(queues = WBT_UPDATED_QUEUE)
    public void wbtUpdated(final WBTUpdatedEvent event) {
        LOGGER.info(
                "Weighbridge ticket updated for license plate {}",
                event.licensePlate()
        );

        // Create and execute the command to update warehouse capacity
        UpdateWarehouseCapacityCommand command = new UpdateWarehouseCapacityCommand(
                new WarehouseId(event.warehouseId()),
                event.netWeight()
        );
        updateWarehouseCapacityUseCase.updateWarehouseCapacity(command);
    }
}