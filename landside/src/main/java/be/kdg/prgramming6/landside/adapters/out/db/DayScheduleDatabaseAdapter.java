package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.landside.port.out.UpdateAppointmentPort;
import be.kdg.prgramming6.landside.port.out.CreateSchedulePort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DayScheduleDatabaseAdapter implements LoadDaySchedulePort, UpdateAppointmentPort, CreateSchedulePort {
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final AppointmentJpaRepository appointmentJpaRepository;

    public DayScheduleDatabaseAdapter(ScheduleJpaRepository scheduleJpaRepository, AppointmentJpaRepository appointmentJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.appointmentJpaRepository = appointmentJpaRepository;
    }

    @Override
    public Optional<Schedule> loadDaySchedule(LocalDateTime scheduleTime) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepository.findByScheduleTime(scheduleTime);
        return Optional.ofNullable(toSchedule(scheduleJpaEntity));
    }

    @Override
    public Optional<Schedule> createSchedule(LocalDateTime scheduleTime) {
        // Check if a schedule for the given day already exists
        if (scheduleJpaRepository.findByScheduleTime(scheduleTime) != null) {
            return Optional.empty(); // Schedule already exists
        }

        Schedule schedule = new Schedule(scheduleTime);
        ScheduleJpaEntity scheduleJpaEntity = new ScheduleJpaEntity();
        scheduleJpaEntity.setScheduleTime(scheduleTime);

        // Add appointments to the schedule
        schedule.getAppointments().forEach(appointment -> {
            AppointmentJpaEntity appointmentJpaEntity = createAppointmentJpaEntity(appointment, scheduleJpaEntity);
            scheduleJpaEntity.getAppointments().add(appointmentJpaEntity);
        });

        scheduleJpaRepository.save(scheduleJpaEntity);
        return Optional.of(schedule); // Return the newly created schedule wrapped in an Optional
    }

    private Schedule toSchedule(ScheduleJpaEntity scheduleJpaEntity) {
        if (scheduleJpaEntity == null) {
            return null; // Or throw an exception, depending on your design
        }

        Schedule schedule = new Schedule(scheduleJpaEntity.getScheduleTime());

        // Add each appointment to the domain Schedule
        scheduleJpaEntity.getAppointments().forEach(appointmentJpaEntity -> {
            Appointment appointment = toAppointment(appointmentJpaEntity);
            schedule.addAppointment(appointment);
        });

        return schedule; // Return the new schedule with the appointments
    }

    private Appointment toAppointment(AppointmentJpaEntity appointmentJpaEntity) {
        return new Appointment(
                new Truck(new LicensePlate(appointmentJpaEntity.getLicensePlate())),
                MaterialType.valueOf(appointmentJpaEntity.getMaterialType()),
                appointmentJpaEntity.getWindowStart(),
                appointmentJpaEntity.getWindowEnd(),
                new SellerId(appointmentJpaEntity.getSellerId())
        );
    }

    private AppointmentJpaEntity createAppointmentJpaEntity(Appointment appointment, ScheduleJpaEntity scheduleJpaEntity) {
        AppointmentJpaEntity appointmentJpaEntity = new AppointmentJpaEntity();
        appointmentJpaEntity.setLicensePlate(appointment.getTruck().getLicensePlate().plateNumber());
        appointmentJpaEntity.setMaterialType(appointment.getMaterialType().name());
        appointmentJpaEntity.setWindowStart(appointment.getWindowStart());
        appointmentJpaEntity.setWindowEnd(appointment.getWindowEnd());
        appointmentJpaEntity.setSellerId(appointment.getSellerId().id());
        appointmentJpaEntity.setSchedule(scheduleJpaEntity); // Set the schedule reference
        return appointmentJpaEntity;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        // Load the existing schedule for the day
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepository.findByScheduleTime(appointment.getWindowStart());

        if (scheduleJpaEntity != null) {
            // Update or add the appointment
            updateOrAddAppointment(appointment, scheduleJpaEntity);
            // Save all appointments to the database
            scheduleJpaRepository.save(scheduleJpaEntity);
            System.out.println("Updated/Saved Appointment for License Plate: " + appointment.getTruck().getLicensePlate().plateNumber());
        }
    }

    private void updateOrAddAppointment(Appointment appointment, ScheduleJpaEntity scheduleJpaEntity) {
        // Check if the appointment exists and update it
        boolean appointmentExists = scheduleJpaEntity.getAppointments().stream()
                .anyMatch(existingAppointment -> existingAppointment.getLicensePlate().equals(appointment.getTruck().getLicensePlate().plateNumber()));

        if (appointmentExists) {
            // Update existing appointment
            scheduleJpaEntity.getAppointments().stream()
                    .filter(existingAppointment -> existingAppointment.getLicensePlate().equals(appointment.getTruck().getLicensePlate().plateNumber()))
                    .forEach(existingAppointment -> updateAppointmentDetails(existingAppointment, appointment));
        } else {
            // Create and add a new appointment
            AppointmentJpaEntity newAppointment = createAppointmentJpaEntity(appointment, scheduleJpaEntity);
            scheduleJpaEntity.getAppointments().add(newAppointment);
        }
    }

    private void updateAppointmentDetails(AppointmentJpaEntity existingAppointment, Appointment appointment) {
        existingAppointment.setLicensePlate(appointment.getTruck().getLicensePlate().plateNumber());
        existingAppointment.setMaterialType(appointment.getMaterialType().name());
        existingAppointment.setWindowStart(appointment.getWindowStart());
        existingAppointment.setWindowEnd(appointment.getWindowEnd());
        existingAppointment.setSellerId(appointment.getSellerId().id());
        existingAppointment.setSchedule(existingAppointment.getSchedule()); // Ensure the schedule reference is set
    }
}
