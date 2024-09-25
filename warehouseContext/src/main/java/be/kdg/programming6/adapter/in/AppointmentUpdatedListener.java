package be.kdg.programming6.adapter.in;

import be.kdg.prgramming6.events.AppointmentUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
public class AppointmentUpdatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentUpdatedListener.class);

    public static final String APPOINTMENT_UPDATED_QUEUE = "appointment_updated";

    @RabbitListener(queues = APPOINTMENT_UPDATED_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void appointmentUpdated(final AppointmentUpdatedEvent event){

        LOGGER.info(
                "Appointment from {} has now the status of {}",
                event.sellerId(),
                event.licensePLate()
        );

    }
}
