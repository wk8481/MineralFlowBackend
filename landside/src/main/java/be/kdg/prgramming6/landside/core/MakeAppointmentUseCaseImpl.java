package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.domain.Schedule;
import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.landside.port.in.MakeAppointmentUseCase;
import be.kdg.prgramming6.landside.port.out.CreateSchedulePort; // Importing the CreateSchedulePort
import be.kdg.prgramming6.landside.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.landside.port.out.UpdateAppointmentPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MakeAppointmentUseCaseImpl implements MakeAppointmentUseCase {
    private final LoadDaySchedulePort loadDaySchedulePort;
    private final CreateSchedulePort createSchedulePort; // New dependency
    private final List<UpdateAppointmentPort> updateAppointmentPorts;


    public MakeAppointmentUseCaseImpl(
            LoadDaySchedulePort loadDaySchedulePort,
            CreateSchedulePort createSchedulePort, // Injecting CreateSchedulePort
            List<UpdateAppointmentPort> updateAppointmentPorts
            ) {
        this.loadDaySchedulePort = loadDaySchedulePort;
        this.createSchedulePort = createSchedulePort; // Assigning to field
        this.updateAppointmentPorts = updateAppointmentPorts;

    }

    @Override
    @Transactional
    public void makeAppointment(MakeAppointmentCommand command) {


        LocalDateTime start = command.appointmentWindowStart().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = command.appointmentWindowEnd().withMinute(0).withSecond(0).withNano(0);

        // Load the schedule for the day or create a new one if it does not exist
        Schedule schedule = loadDaySchedulePort.loadDaySchedule(start)
                .orElseGet(() -> {
                    // If the schedule does not exist, create it
                    return createSchedulePort.createSchedule(start)
                            .orElseThrow(() -> new RuntimeException("Unable to create a new schedule for the date: %s".formatted(start.toLocalDate())));
                });

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
