package be.kdg.prgramming6.landside.adapters.out;

import be.kdg.prgramming6.common.events.WBTUpdatedEvent;
import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
import be.kdg.prgramming6.landside.port.out.UpdateWBTPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;



public class WBTUpdatedPublisher implements UpdateWBTPort {
    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "landside_events";

    private final static Logger LOGGER = LoggerFactory.getLogger(WBTUpdatedPublisher.class.getName());

    public WBTUpdatedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void update(WeighbridgeTicket weighbridgeTicket, Truck truck) {
        final String routingKey = "landside." + weighbridgeTicket.getLicensePlate() + ".weighbridgeTicket.updated";
        LOGGER.info("Notifying RabbitMQ: {}", routingKey);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, new WBTUpdatedEvent(
                truck.getSellerId().id(),
                weighbridgeTicket.getNetWeight(),
                truck.getMaterialType().toString(),
                weighbridgeTicket.getLicensePlate()


        ));

    }

    @Override
    public void update(WeighbridgeTicket weighbridgeTicket) {

    }


}
