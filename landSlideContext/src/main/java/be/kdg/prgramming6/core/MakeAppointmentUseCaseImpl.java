package be.kdg.prgramming6.core;

import be.kdg.prgramming6.domain.Appointment;
import be.kdg.prgramming6.domain.Schedule;
import be.kdg.prgramming6.exception.NotFoundException;
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import be.kdg.prgramming6.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MakeAppointmentUseCaseImpl implements MakeAppointmentUseCase {
    private final LoadDaySchedulePort loadDaySchedulePort;
    private final List<UpdateAppointmentPort> updateAppointmentPorts;

    public MakeAppointmentUseCaseImpl(LoadDaySchedulePort loadDaySchedulePort, List<UpdateAppointmentPort> updateAppointmentPorts) {
        this.loadDaySchedulePort = loadDaySchedulePort;
        this.updateAppointmentPorts = updateAppointmentPorts;
    }

    @Override
    @Transactional
    public void makeAppointment(MakeAppointmentCommand command) {
        LocalDateTime start = command.appointmentWindowStart().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = command.appointmentWindowEnd().withMinute(0).withSecond(0).withNano(0);

        // Load the schedule for the day
        Schedule schedule = loadDaySchedulePort.loadDaySchedule(start.toLocalDate())
                .orElseThrow(() -> new NotFoundException("Schedule for date %s not found.".formatted(start.toLocalDate())));

        // Delegate scheduling logic to Schedule class and capture the appointment created
        Appointment appointment = schedule.scheduleAppointment(
                command.sellerId(),
                command.licensePlate(),
                command.materialType(),
                start,
                end
        );

        // Update the appointment via each output port
        updateAppointmentPorts.forEach(port -> port.updateAppointment(appointment));

        // Notify about the new appointment
        System.out.println("Appointment successfully scheduled.");
    }
}
