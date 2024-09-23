package be.kdg.prgramming6.core;

import be.kdg.prgramming6.domain.Appointment;
import be.kdg.prgramming6.domain.Schedule;
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MakeAppointmentUseCaseImpl implements MakeAppointmentUseCase {
    private final Schedule schedule = new Schedule(); // Use Schedule to handle appointment logic
    private final UpdateAppointmentPort updateAppointmentPort;

    public MakeAppointmentUseCaseImpl(UpdateAppointmentPort updateAppointmentPort) {
        this.updateAppointmentPort = updateAppointmentPort;
    }

    @Override
    public void makeAppointment(MakeAppointmentCommand command) {
        LocalDateTime start = command.appointmentWindowStart().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = command.appointmentWindowEnd().withMinute(0).withSecond(0).withNano(0);

        // Delegate scheduling logic to Schedule class and capture the appointment created
        Appointment appointment = schedule.scheduleAppointment(
                command.sellerId(),
                command.licensePlate(),
                command.materialType(),
                start,
                end
        );

        // Pass the appointment to the output port
        updateAppointmentPort.updateAppointment(appointment);

        // Notify about the new appointment
        System.out.println("Appointment successfully scheduled.");
    }
}
