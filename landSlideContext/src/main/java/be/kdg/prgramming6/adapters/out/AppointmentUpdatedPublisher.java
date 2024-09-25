package be.kdg.prgramming6.adapters.out;

import be.kdg.prgramming6.domain.Appointment;
import be.kdg.prgramming6.events.AppointmentUpdatedEvent;
import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentUpdatedPublisher implements UpdateAppointmentPort {
    private final ApplicationEventPublisher publisher;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentUpdatedPublisher.class);

    public AppointmentUpdatedPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        logger.info("Received appointment update request.");

        // Extract primitive types
        UUID sellerIdValue = appointment.getAppointmentId();  // Assuming SellerId has a method id() that returns a UUID
        String licensePlateValue = appointment.getTruck().licensePlate().plateNumber();  // Extract the plate number string

        // Log the appointment update
        logger.info("Updating appointment for Seller ID: {} with License Plate: {}", sellerIdValue, licensePlateValue);

        // Publish event using primitive values
        publisher.publishEvent(new AppointmentUpdatedEvent(
                sellerIdValue,
                licensePlateValue
        ));

        // Log the event publication
        logger.info("Published AppointmentUpdatedEvent for Seller ID: {} and License Plate: {}", sellerIdValue, licensePlateValue);
    }
}
