package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.common.events.WarehouseActivityCreatedEvent;
import be.kdg.prgramming6.landside.port.in.WarehouseProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ActivityCreatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCreatedListener.class);
    private static final String WAREHOUSE_ACTIVITY_CREATED_QUEUE = "warehouse_activity_created";

    private final WarehouseProjector warehouseProjector;

    public ActivityCreatedListener(WarehouseProjector warehouseProjector) {
        this.warehouseProjector = warehouseProjector;
    }

    @RabbitListener(queues = WAREHOUSE_ACTIVITY_CREATED_QUEUE)
    public void activityCreated(final WarehouseActivityCreatedEvent event) {
        try {
            LOGGER.info(
                    "{} activity got created on warehouse of {} with weight of {}",
                    event.type(),
                    event.sellerId(),
                    event.weight()
            );
            warehouseProjector.projectWarehouse(event.warehouseId(), event.type(), event.sellerId(), event.materialType(), event.time(), event.weight());
        } catch (IllegalStateException e) {
            LOGGER.error("Error processing warehouse activity: {}", e.getMessage());
        }
    }
}