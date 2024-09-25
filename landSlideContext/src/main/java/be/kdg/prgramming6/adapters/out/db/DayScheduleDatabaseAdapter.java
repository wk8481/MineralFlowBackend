package be.kdg.prgramming6.adapters.out.db;

import be.kdg.prgramming6.domain.*;
import be.kdg.prgramming6.port.out.LoadDaySchedulePort;
import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DayScheduleDatabaseAdapter implements LoadDaySchedulePort, UpdateAppointmentPort {
    private final ScheduleJpaRepository scheduleJpaRepository;

    public DayScheduleDatabaseAdapter(ScheduleJpaRepository scheduleJpaRepository) {
        this.scheduleJpaRepository = scheduleJpaRepository;
    }

    @Override
    public Optional<Schedule> loadDaySchedule(LocalDate day) {
        // Fetch the schedule for the specified day
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepository.findByDay(day);
        return Optional.ofNullable(toSchedule(scheduleJpaEntity));
    }

    private Schedule toSchedule(ScheduleJpaEntity scheduleJpaEntity) {
        if (scheduleJpaEntity == null) {
            return null; // Or throw an exception, depending on your design
        }

        Schedule schedule = new Schedule();

        // Add each appointment to the domain Schedule
        scheduleJpaEntity.getAppointments().forEach(appointmentJpaEntity -> {
            Appointment appointment = toAppointment(appointmentJpaEntity);
            schedule.addAppointment(appointment); // Use the add method
        });

        return schedule; // Return the new schedule and include the appointments
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
        //Convert Appointment to AppointmentJpaEntity

        AppointmentJpaEntity appointmentJpaEntity = new AppointmentJpaEntity();
        appointmentJpaEntity.setAppointmentId(appointment.getAppointmentId());
        appointmentJpaEntity.setLicensePlate(appointment.getTruck().licensePlate().plateNumber());
        appointmentJpaEntity.setMaterialType(appointment.getMaterialType().name());
        appointmentJpaEntity.setWindowStart(appointment.getWindowStart());
        appointmentJpaEntity.setWindowEnd(appointment.getWindowEnd());
        appointmentJpaEntity.setSellerId(appointment.getSellerId().id());

        // Save the appointment to the database and use the returned value if needed
        AppointmentJpaEntity savedEntity = scheduleJpaRepository.save(appointmentJpaEntity);

        // Optionally, you can do something with the saved entity, such as logging
        // or further processing, if necessary.
        System.out.println("Saved Appointment ID: " + savedEntity.getAppointmentId());
    }

}
