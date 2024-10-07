package be.kdg.prgramming6.landside.adapters.out;

import be.kdg.prgramming6.common.events.AppointmentUpdatedEvent;
import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.out.UpdateAppointmentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentUpdatedPublisher implements UpdateAppointmentPort {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentUpdatedPublisher.class);

    public AppointmentUpdatedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        logger.info("Received appointment update request.");

        // Extract primitive types
        UUID sellerIdValue = appointment.getAppointmentId().appointmentId();  // Assuming SellerId has a method id() that returns a UUID
        String licensePlateValue = appointment.getTruck().licensePlate().plateNumber();  // Extract the plate number string

        // Log the appointment update
        logger.info("Updating appointment for Seller ID: {} with License Plate: {}", sellerIdValue, licensePlateValue);

        // Publish event using primitive values
        AppointmentUpdatedEvent appointmentUpdatedEvent = new AppointmentUpdatedEvent(
                sellerIdValue,
                licensePlateValue
        );

        String routingKey = "landslide.%s.appointment.updated".formatted(sellerIdValue.toString());

        this.rabbitTemplate.convertAndSend(
                RabbitMQTopology.LANDSLIDE_EVENTS_EXCHANGE,
                routingKey,
                appointmentUpdatedEvent
        );


        // Log the event publication
        logger.info("Published AppointmentUpdatedEvent for Seller ID: {} and License Plate: {}", sellerIdValue, licensePlateValue);
    }
}
