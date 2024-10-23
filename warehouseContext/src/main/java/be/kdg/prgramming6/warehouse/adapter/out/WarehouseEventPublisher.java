package be.kdg.prgramming6.warehouse.adapter.out;

import be.kdg.prgramming6.common.events.WarehouseActivityCreatedEvent;
import be.kdg.prgramming6.warehouse.domain.Warehouse;
import be.kdg.prgramming6.warehouse.domain.WarehouseActivity;
import be.kdg.prgramming6.warehouse.port.out.UpdateWarehousePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class WarehouseEventPublisher implements UpdateWarehousePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseEventPublisher.class);

    private static final String EXCHANGE_NAME = "warehouse_events";

    private final RabbitTemplate rabbitTemplate;

    public WarehouseEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void activityCreated(Warehouse warehouse, WarehouseActivity warehouseActivity) {
        final String routingKey = "warehouse." + warehouse.getId().id() + ".activity.created";
        LOGGER.info("Notifying RabbitMQ: {}", routingKey);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, new WarehouseActivityCreatedEvent(
                warehouse.getId().id(), // Assuming getId() returns a WarehouseId, and getValue() returns a UUID
                warehouseActivity.warehouseActivityType(),
                warehouseActivity.materialType().toString(), // Assuming materialType() returns a MaterialType, and toString() returns a String
                warehouse.getSellerId().id(), // Assuming getSellerId() returns a sellerId, and getValue() returns a UUID
                warehouseActivity.time(),
                warehouseActivity.weight()
        ));
    }
}



