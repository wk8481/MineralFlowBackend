package be.kdg.prgramming6.adapters.out.db;

import be.kdg.prgramming6.domain.*;
import be.kdg.prgramming6.port.out.CreateSchedulePort;
import be.kdg.prgramming6.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
import be.kdg.prgramming6.port.out.CreateAppointmentPort; // Import your interface
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DayScheduleDatabaseAdapter implements LoadDaySchedulePort, UpdateAppointmentPort, CreateSchedulePort, CreateAppointmentPort {
    private final ScheduleJpaRepository scheduleJpaRepository;

    public DayScheduleDatabaseAdapter(ScheduleJpaRepository scheduleJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
    }

    @Override
    public Optional<Schedule> loadDaySchedule(LocalDate day) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepository.findByDay(day);
        return Optional.ofNullable(toSchedule(scheduleJpaEntity));
    }

    @Override
    public Optional<Schedule> createSchedule(LocalDate day) {
        // Check if a schedule for the given day already exists
        if (scheduleJpaRepository.findByDay(day) != null) {
            return Optional.empty(); // Schedule already exists
        }

        Schedule schedule = new Schedule(day);
        ScheduleJpaEntity scheduleJpaEntity = new ScheduleJpaEntity();
        scheduleJpaEntity.setDay(day);
        scheduleJpaRepository.save(scheduleJpaEntity);

        return Optional.of(schedule); // Return the newly created schedule wrapped in an Optional
    }

    private Schedule toSchedule(ScheduleJpaEntity scheduleJpaEntity) {
        if (scheduleJpaEntity == null) {
            return null; // Or throw an exception, depending on your design
        }

        Schedule schedule = new Schedule(scheduleJpaEntity.getDay());

        // Add each appointment to the domain Schedule
        scheduleJpaEntity.getAppointments().forEach(appointmentJpaEntity -> {
            Appointment appointment = toAppointment(appointmentJpaEntity);
            schedule.addAppointment(appointment);
        });

        return schedule; // Return the new schedule with the appointments
    }

    private Appointment toAppointment(AppointmentJpaEntity appointmentJpaEntity) {
        return new Appointment(
                appointmentJpaEntity.getAppointmentId(),
                new Truck(new LicensePlate(appointmentJpaEntity.getLicensePlate())),
                MaterialType.valueOf(appointmentJpaEntity.getMaterialType()),
                appointmentJpaEntity.getWindowStart(),
                appointmentJpaEntity.getWindowEnd(),
                new SellerId(appointmentJpaEntity.getSellerId())
        );
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        AppointmentJpaEntity appointmentJpaEntity = new AppointmentJpaEntity();
        appointmentJpaEntity.setAppointmentId(appointment.getAppointmentId());
        appointmentJpaEntity.setLicensePlate(appointment.getTruck().licensePlate().plateNumber());
        appointmentJpaEntity.setMaterialType(appointment.getMaterialType().name());
        appointmentJpaEntity.setWindowStart(appointment.getWindowStart());
        appointmentJpaEntity.setWindowEnd(appointment.getWindowEnd());
        appointmentJpaEntity.setSellerId(appointment.getSellerId().id());

        AppointmentJpaEntity savedEntity = scheduleJpaRepository.save(appointmentJpaEntity);
        System.out.println("Saved Appointment ID: " + savedEntity.getAppointmentId());
    }

    @Override
    public void createAppointment(Appointment appointment) {
        AppointmentJpaEntity appointmentJpaEntity = new AppointmentJpaEntity();
        appointmentJpaEntity.setAppointmentId(appointment.getAppointmentId());
        appointmentJpaEntity.setLicensePlate(appointment.getTruck().licensePlate().plateNumber());
        appointmentJpaEntity.setMaterialType(appointment.getMaterialType().name());
        appointmentJpaEntity.setWindowStart(appointment.getWindowStart());
        appointmentJpaEntity.setWindowEnd(appointment.getWindowEnd());
        appointmentJpaEntity.setSellerId(appointment.getSellerId().id());

        scheduleJpaRepository.save(appointmentJpaEntity);
        System.out.println("Saved Appointment ID: " + appointmentJpaEntity.getAppointmentId());
    }
}
