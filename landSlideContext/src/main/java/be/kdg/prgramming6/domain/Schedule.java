package be.kdg.prgramming6.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Schedule {
    private final LocalDate day; // Store the day for this schedule
    private final List<Appointment> appointments = new ArrayList<>(); // Store the appointments

    // Constructor to initialize the schedule with a specific day
    public Schedule(LocalDate day) {
        this.day = day;
    }

    public LocalDate getDay() {
        return day;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    // Method to check availability and schedule an appointment
    public Appointment scheduleAppointment(SellerId sellerId, LicensePlate licensePlate, MaterialType materialType,
                                           LocalDateTime windowStart, LocalDateTime windowEnd) {

        // Check if there is availability in the schedule
        if (!hasAvailability(windowStart, windowEnd)) {
            throw new IllegalStateException("No available slots for the selected time window");
        }

        // Create and store the appointment
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = Appointment.scheduleAppointment(
                appointmentId,
                new Truck(licensePlate),
                materialType,
                windowStart,
                windowEnd,
                sellerId
        );

        addAppointment(appointment); // Add the appointment to the schedule

        System.out.println("Appointment created with ID: " + appointment.getAppointmentId());

        // Return the newly created appointment
        return appointment;
    }

    // Method to check if the requested time window is available
    public boolean hasAvailability(LocalDateTime start, LocalDateTime end) {
        // Business rule: Only allow 40 trucks per time window
        long overlappingAppointments = appointments.stream()
                .filter(appointment -> appointment.overlapsWith(start, end))
                .count();

        return overlappingAppointments < 40; // Limiting to 40 trucks per time window
    }
}
