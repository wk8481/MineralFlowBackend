package be.kdg.prgramming6.landside.adapter.out;

import be.kdg.prgramming6.common.events.AppointmentUpdatedEvent;
import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.out.UpdateAppointmentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentUpdatedPublisher implements UpdateAppointmentPort {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentUpdatedPublisher.class);

    private static final String EXCHANGE_NAME = "landside_events";


    public AppointmentUpdatedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        final String routingKey = "landside." + appointment.getSellerId().id() + ".appointment.updated";
        LOGGER.info("Notifying RabbitMQ: {}", routingKey);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, new AppointmentUpdatedEvent(
                appointment.getSellerId().id(),
                appointment.getTruck().getLicensePlate().value()
        ));

    }
}

