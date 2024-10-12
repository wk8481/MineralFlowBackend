package be.kdg.prgramming6.landside.adapters.in;

import be.kdg.prgramming6.common.events.WarehouseActivityCreatedEvent;
import be.kdg.prgramming6.landside.port.in.WarehouseProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ActivityCreatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCreatedListener.class);
    private static final String CREATED_ACTIVITIES_QUEUE = "created_activities";

    private final WarehouseProjector warehouseProjector;

    public ActivityCreatedListener(WarehouseProjector warehouseProjector) {
        this.warehouseProjector = warehouseProjector;
    }
    //include da checkCapacityForAppointmentUseCase

    @RabbitListener(queues = CREATED_ACTIVITIES_QUEUE)
    public void activityCreated(final WarehouseActivityCreatedEvent event) {
        LOGGER.info(
            "{} activity got created on warehouse of {} with weight of {}",
            event.type(),
            event.sellerId(), //sellerId or warehouseId?
            event.weight()
        );
        warehouseProjector.projectWarehouse(event.warehouseId(), event.type(), event.sellerId(), event.materialType(), event.time(), event.weight());
        //checkCapacityForAppointmentUseCase.checkCapacityForAppointment(event.sellerId());
    }
}
