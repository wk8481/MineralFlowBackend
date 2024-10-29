package be.kdg.prgramming6.landside.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private final LocalDateTime scheduleTime;
    private final List<Appointment> appointments = new ArrayList<>();

    public Schedule(LocalDateTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public LocalDateTime getScheduleTime() {
        return scheduleTime;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public Appointment scheduleAppointment(SellerId sellerId, LicensePlate licensePlate, MaterialType materialType,
                                           LocalDateTime windowStart, LocalDateTime windowEnd, Warehouse warehouse) {
        // Ensure windowEnd is correctly set to 59 minutes past the hour if it is incorrectly set to the same as windowStart
        if (windowEnd.equals(windowStart)) {
            windowEnd = windowStart.plusMinutes(59);
        }

        if (!isAppointmentPossible(windowStart, windowEnd, warehouse)) {
            throw new IllegalStateException("Cannot schedule appointment: either the warehouse is over 80% full or there are already 40 appointments in the same hour.");
        }

        Appointment appointment = Appointment.scheduleAppointment(
                new Truck(licensePlate),
                materialType,
                windowStart,
                windowEnd,
                sellerId
        );

        addAppointment(appointment);
        System.out.println("Appointment created for truck with license plate: " + licensePlate);
        return appointment;
    }

    public boolean hasAvailability(LocalDateTime start, LocalDateTime end) {
        long overlappingAppointments = appointments.stream()
                .filter(appointment -> appointment.overlapsWith(start, end) || appointment.isWithinWindow(start) || appointment.isWithinWindow(end))
                .count();

        return overlappingAppointments < 40;
    }

    private boolean isAppointmentPossible(LocalDateTime start, LocalDateTime end, Warehouse warehouse) {
        BigDecimal eightyPercentCapacity = Warehouse.getMaxCapacity().multiply(BigDecimal.valueOf(0.8));
        return warehouse.getCurrentCapacity().compareTo(eightyPercentCapacity) < 0 || hasAvailability(start, end);
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }
}