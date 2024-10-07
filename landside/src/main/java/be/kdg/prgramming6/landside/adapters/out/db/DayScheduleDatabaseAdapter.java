package be.kdg.prgramming6.landside.adapters.out.db;


import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.landside.port.out.UpdateAppointmentPort;
import be.kdg.prgramming6.landside.port.out.CreateSchedulePort;
import be.kdg.prgramming6.landside.port.out.CheckAppointmentPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;


@Component
public class DayScheduleDatabaseAdapter implements LoadDaySchedulePort, UpdateAppointmentPort, CreateSchedulePort, CheckAppointmentPort {
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final AppointmentJpaRepository appointmentJpaRepository;

    public DayScheduleDatabaseAdapter(ScheduleJpaRepository scheduleJpaRepository, AppointmentJpaRepository appointmentJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.appointmentJpaRepository = appointmentJpaRepository;
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

        // Set the schedule for each appointment if any
        for (Appointment appointment : schedule.getAppointments()) {
            AppointmentJpaEntity appointmentJpaEntity = new AppointmentJpaEntity();
            appointmentJpaEntity.setAppointmentId(appointment.getAppointmentId().appointmentId());
            appointmentJpaEntity.setLicensePlate(appointment.getTruck().getLicensePlate().plateNumber());
            appointmentJpaEntity.setMaterialType(appointment.getMaterialType().name());
            appointmentJpaEntity.setWindowStart(appointment.getWindowStart());
            appointmentJpaEntity.setWindowEnd(appointment.getWindowEnd());
            appointmentJpaEntity.setSellerId(appointment.getSellerId().id());
            appointmentJpaEntity.setSchedule(scheduleJpaEntity); // Set the schedule reference
            scheduleJpaEntity.getAppointments().add(appointmentJpaEntity);
        }

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
            Appointment appointment = toAppointment(appointmentJpaEntity, scheduleJpaEntity);
            schedule.addAppointment(appointment);
        });

        return schedule; // Return the new schedule with the appointments
    }

    private Appointment toAppointment(AppointmentJpaEntity appointmentJpaEntity, ScheduleJpaEntity scheduleJpaEntity) {
        Appointment appointment = new Appointment(
                new AppointmentId(appointmentJpaEntity.getAppointmentId()),
                new Truck(new LicensePlate(appointmentJpaEntity.getLicensePlate())),
                MaterialType.valueOf(appointmentJpaEntity.getMaterialType()),
                appointmentJpaEntity.getWindowStart(),
                appointmentJpaEntity.getWindowEnd(),
                new SellerId(appointmentJpaEntity.getSellerId())
        );

        // Set the schedule for the appointment
        appointmentJpaEntity.setSchedule(scheduleJpaEntity); // Make sure to set the schedule

        return appointment; // Return the new appointment
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        // Load the existing schedule for the day
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepository.findByDay(appointment.getWindowStart().toLocalDate());

        if (scheduleJpaEntity != null) {
            // Check if the appointment exists and update it
            boolean appointmentExists = scheduleJpaEntity.getAppointments().stream()
                    .anyMatch(existingAppointment -> existingAppointment.getAppointmentId().equals(appointment.getAppointmentId()));

            if (appointmentExists) {
                // Update existing appointment
                scheduleJpaEntity.getAppointments().stream()
                        .filter(existingAppointment -> existingAppointment.getAppointmentId().equals(appointment.getAppointmentId()))
                        .forEach(existingAppointment -> {
                            existingAppointment.setLicensePlate(appointment.getTruck().getLicensePlate().plateNumber());
                            existingAppointment.setMaterialType(appointment.getMaterialType().name());
                            existingAppointment.setWindowStart(appointment.getWindowStart());
                            existingAppointment.setWindowEnd(appointment.getWindowEnd());
                            existingAppointment.setSellerId(appointment.getSellerId().id());
                            existingAppointment.setSchedule(scheduleJpaEntity); // Ensure the schedule reference is set
                        });
            } else {
                // Create and add a new appointment
                AppointmentJpaEntity newAppointment = new AppointmentJpaEntity();
                newAppointment.setAppointmentId(appointment.getAppointmentId().appointmentId());
                newAppointment.setLicensePlate(appointment.getTruck().getLicensePlate().plateNumber());
                newAppointment.setMaterialType(appointment.getMaterialType().name());
                newAppointment.setWindowStart(appointment.getWindowStart());
                newAppointment.setWindowEnd(appointment.getWindowEnd());
                newAppointment.setSellerId(appointment.getSellerId().id());
                newAppointment.setSchedule(scheduleJpaEntity); // Set the schedule reference

                // Add the new appointment to the existing list
                scheduleJpaEntity.getAppointments().add(newAppointment);
            }

            // Save all appointments to the database
            scheduleJpaRepository.save(scheduleJpaEntity);
            System.out.println("Updated/Saved Appointment ID: " + appointment.getAppointmentId());
        }
    }


    @Override
    public boolean existsById(AppointmentId appointmentId) {
        return appointmentJpaRepository.existsById(appointmentId.appointmentId());
    }
}
