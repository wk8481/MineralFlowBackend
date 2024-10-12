package be.kdg.prgramming6.warehouse.adapter.in.out;

import be.kdg.prgramming6.warehouse.domain.Warehouse;
import be.kdg.prgramming6.warehouse.domain.WarehouseActivity;
import be.kdg.prgramming6.warehouse.port.in.out.UpdateWarehousePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class WarehouseEventPublisher implements UpdateWarehousePort {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WarehouseEventPublisher.class);

    public WarehouseEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }



    @Override
    public void activityCreated(Warehouse warehouse, WarehouseActivity activity) {
        logger.info("Received warehouse activity update request.");

        // Extract primitive types
        String warehouseIdValue = String.valueOf(warehouse.getId().id()); // Use the correct method to get WarehouseId
        String activityTypeValue = activity.warehouseActivityType().name(); // Extract the activity type string

        // Log the warehouse activity update
        logger.info("Updating warehouse activity for Warehouse ID: {} with Activity Type: {}", warehouseIdValue, activityTypeValue);

        // Publish event using primitive values
        WarehouseActivityUpdatedEvent warehouseActivityUpdatedEvent = new WarehouseActivityUpdatedEvent(
                warehouseIdValue,
                activityTypeValue
        );

        // Use a dynamic routing key based on warehouse ID
        String routingKey = String.format("warehouse.%s.warehouse.updated", warehouseIdValue);

        try {
            // Send the message to the RabbitMQ exchange
            this.rabbitTemplate.convertAndSend(
                    RabbitMQTopology.WAREHOUSE_EVENTS_EXCHANGE
            );

            // Log the event publication
            logger.info("Published WarehouseActivityUpdatedEvent for Warehouse ID: {} and Activity Type: {}", warehouseIdValue, activityTypeValue);
        } catch (Exception e) {
            // Log any errors that occur during message publishing
            logger.error("Failed to publish WarehouseActivityUpdatedEvent for Warehouse ID: {} and Activity Type: {}. Error: {}", warehouseIdValue, activityTypeValue, e.getMessage());
        }

    }
}

