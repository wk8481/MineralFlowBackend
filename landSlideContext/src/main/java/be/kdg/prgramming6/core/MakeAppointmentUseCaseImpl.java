package be.kdg.prgramming6.core;

import be.kdg.prgramming6.domain.Appointment;
import be.kdg.prgramming6.domain.Truck;
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MakeAppointmentUseCaseImpl implements MakeAppointmentUseCase {

    private final Map<LocalDateTime, Integer> appointmentsPerHour = new HashMap<>();

    @Override
    public void makeAppointment(MakeAppointmentCommand command) {
        LocalDateTime start = command.appointmentWindowStart().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = command.appointmentWindowEnd().withMinute(0).withSecond(0).withNano(0);

        validateSlotAvailability(start, end);

        // Generate a unique identifier for the appointment
        UUID appointmentId = UUID.randomUUID();

        // Create the appointment using the domain method
        Appointment appointment = Appointment.scheduleAppointment(
                appointmentId,
                new Truck(command.licensePlate()),
                command.licensePlate(),
                command.materialType(),
                start,
                end
        );

        // Save the appointment (update the in-memory store for simplicity)
        for (LocalDateTime dateTime = start; !dateTime.isAfter(end); dateTime = dateTime.plusHours(1)) {
            appointmentsPerHour.put(dateTime, appointmentsPerHour.getOrDefault(dateTime, 0) + 1);
        }

        // Notify the truck (e.g., log an event or send a message)
        System.out.println("Appointment created with ID: " + appointment.getAppointmentId());
    }

    private void validateSlotAvailability(LocalDateTime start, LocalDateTime end) {
        int maxAppointmentsPerHour = 40;

        for (LocalDateTime dateTime = start; !dateTime.isAfter(end); dateTime = dateTime.plusHours(1)) {
            int currentCount = appointmentsPerHour.getOrDefault(dateTime, 0);
            if (currentCount >= maxAppointmentsPerHour) {
                throw new IllegalStateException("No available slots for the selected time window");
            }
        }
    }
}

