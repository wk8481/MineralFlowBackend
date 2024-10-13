package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.domain.Schedule;
import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.landside.port.in.MakeAppointmentUseCase;
import be.kdg.prgramming6.landside.port.out.CreateSchedulePort;
import be.kdg.prgramming6.landside.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.landside.port.out.LoadWarehousePort;
import be.kdg.prgramming6.landside.port.out.UpdateAppointmentPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MakeAppointmentUseCaseImpl implements MakeAppointmentUseCase {
    private final LoadDaySchedulePort loadDaySchedulePort;
    private final CreateSchedulePort createSchedulePort;
    private final List<UpdateAppointmentPort> updateAppointmentPorts;
    private final LoadWarehousePort loadWarehousePort;

    public MakeAppointmentUseCaseImpl(
            LoadDaySchedulePort loadDaySchedulePort,
            CreateSchedulePort createSchedulePort,
            List<UpdateAppointmentPort> updateAppointmentPorts,
            LoadWarehousePort loadWarehousePort
    ) {
        this.loadDaySchedulePort = loadDaySchedulePort;
        this.createSchedulePort = createSchedulePort;
        this.updateAppointmentPorts = updateAppointmentPorts;
        this.loadWarehousePort = loadWarehousePort;
    }

    @Override
    @Transactional
    public void makeAppointment(MakeAppointmentCommand command) {
        LocalDateTime start = command.appointmentWindowStart().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = command.appointmentWindowEnd().withMinute(0).withSecond(0).withNano(0);

        Schedule schedule = loadDaySchedulePort.loadDaySchedule(start)
                .orElseGet(() -> createSchedulePort.createSchedule(start)
                        .orElseThrow(() -> new RuntimeException("Unable to create a new schedule for the date: %s".formatted(start.toLocalDate()))));

        Warehouse warehouse = loadWarehousePort.loadWarehouse(command.sellerId(), command.materialType(), LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Warehouse not found for given parameters."));

        Appointment appointment = schedule.scheduleAppointment(
                command.sellerId(),
                command.licensePlate(),
                command.materialType(),
                start,
                end,
                warehouse
        );

        updateAppointmentPorts.forEach(port -> port.updateAppointment(appointment));
        System.out.println("Appointment successfully scheduled.");
    }
}