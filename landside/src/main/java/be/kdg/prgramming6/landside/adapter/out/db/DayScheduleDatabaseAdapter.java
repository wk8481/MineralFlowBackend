package be.kdg.prgramming6.landside.adapter.out.db;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DayScheduleDatabaseAdapter implements LoadDaySchedulePort, UpdateAppointmentPort, CreateSchedulePort, LoadAppointmentPort, LoadTrucksByDaySchedulePort, LoadAppointmentByLicensePlatePort {
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
        if (scheduleJpaRepository.findByScheduleTime(scheduleTime) != null) {
            return Optional.empty();
        }

        Schedule schedule = new Schedule(scheduleTime);
        ScheduleJpaEntity scheduleJpaEntity = new ScheduleJpaEntity();
        scheduleJpaEntity.setScheduleTime(scheduleTime);

        schedule.getAppointments().forEach(appointment -> {
            AppointmentJpaEntity appointmentJpaEntity = createAppointmentJpaEntity(appointment, scheduleJpaEntity);
            scheduleJpaEntity.getAppointments().add(appointmentJpaEntity);
        });

        scheduleJpaRepository.save(scheduleJpaEntity);
        return Optional.of(schedule);
    }

    private Schedule toSchedule(ScheduleJpaEntity scheduleJpaEntity) {
        if (scheduleJpaEntity == null) {
            return null;
        }

        Schedule schedule = new Schedule(scheduleJpaEntity.getScheduleTime());
        scheduleJpaEntity.getAppointments().forEach(appointmentJpaEntity -> {
            Appointment appointment = toAppointment(appointmentJpaEntity);
            schedule.addAppointment(appointment);
        });

        return schedule;
    }
    @Override
    public List<Appointment> loadTrucksByDaySchedules(List<LocalDateTime> scheduleTimes) {
        return scheduleTimes.stream()
                .map(scheduleJpaRepository::findByScheduleTime)
                .filter(Objects::nonNull)
                .flatMap(scheduleJpaEntity -> scheduleJpaEntity.getAppointments().stream())
                .map(this::toAppointment)
                .collect(Collectors.toList());
    }

    private Appointment toAppointment(AppointmentJpaEntity appointmentJpaEntity) {
        Appointment appointment = new Appointment(
                new Truck(new LicensePlate(appointmentJpaEntity.getLicensePlate())),
                MaterialType.valueOf(appointmentJpaEntity.getMaterialType()),
                appointmentJpaEntity.getWindowStart(),
                appointmentJpaEntity.getWindowEnd(),
                new SellerId(appointmentJpaEntity.getSellerId())
        );
        appointment.setArrivalTime(appointmentJpaEntity.getArrivalTime());
        appointment.setDepartureTime(appointmentJpaEntity.getDepartureTime());
        return appointment;
    }

    private AppointmentJpaEntity createAppointmentJpaEntity(Appointment appointment, ScheduleJpaEntity scheduleJpaEntity) {
        AppointmentJpaEntity appointmentJpaEntity = new AppointmentJpaEntity();
        appointmentJpaEntity.setLicensePlate(appointment.getTruck().getLicensePlate().toString());
        appointmentJpaEntity.setMaterialType(appointment.getMaterialType().name());
        appointmentJpaEntity.setWindowStart(appointment.getWindowStart());
        appointmentJpaEntity.setWindowEnd(appointment.getWindowEnd());
        appointmentJpaEntity.setSellerId(appointment.getSellerId().id());
        appointmentJpaEntity.setSchedule(scheduleJpaEntity);
        appointmentJpaEntity.setArrivalTime(appointment.getArrivalTime());
        appointmentJpaEntity.setDepartureTime(appointment.getDepartureTime());
        return appointmentJpaEntity;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepository.findByScheduleTime(appointment.getWindowStart());

        if (scheduleJpaEntity != null) {
            updateOrAddAppointment(appointment, scheduleJpaEntity);
            scheduleJpaRepository.save(scheduleJpaEntity);
            System.out.println("Updated/Saved Appointment for License Plate: " + appointment.getTruck().getLicensePlate().toString());
        }
    }

    private void updateOrAddAppointment(Appointment appointment, ScheduleJpaEntity scheduleJpaEntity) {
        boolean appointmentExists = scheduleJpaEntity.getAppointments().stream()
                .anyMatch(existingAppointment -> existingAppointment.getLicensePlate().equals(appointment.getTruck().getLicensePlate().toString()));

        if (appointmentExists) {
            scheduleJpaEntity.getAppointments().stream()
                    .filter(existingAppointment -> existingAppointment.getLicensePlate().equals(appointment.getTruck().getLicensePlate().toString()))
                    .forEach(existingAppointment -> updateAppointmentDetails(existingAppointment, appointment));
        } else {
            AppointmentJpaEntity newAppointment = createAppointmentJpaEntity(appointment, scheduleJpaEntity);
            scheduleJpaEntity.getAppointments().add(newAppointment);
        }
    }

    private void updateAppointmentDetails(AppointmentJpaEntity existingAppointment, Appointment appointment) {
        existingAppointment.setLicensePlate(appointment.getTruck().getLicensePlate().toString());
        existingAppointment.setMaterialType(appointment.getMaterialType().name());
        existingAppointment.setWindowStart(appointment.getWindowStart());
        existingAppointment.setWindowEnd(appointment.getWindowEnd());
        existingAppointment.setSellerId(appointment.getSellerId().id());
        existingAppointment.setArrivalTime(appointment.getArrivalTime());
        existingAppointment.setDepartureTime(appointment.getDepartureTime());
        existingAppointment.setSchedule(existingAppointment.getSchedule());
    }

    @Override
    public List<Appointment> loadAllAppointments() {
        return appointmentJpaRepository.findAll().stream()
                .map(this::toAppointment)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Appointment> loadAppointmentByLicensePlate(String licensePlate) {
        Optional<AppointmentJpaEntity> appointmentJpaEntity = appointmentJpaRepository.findByLicensePlate(licensePlate);
        return appointmentJpaEntity.map(this::toAppointment);
    }
}