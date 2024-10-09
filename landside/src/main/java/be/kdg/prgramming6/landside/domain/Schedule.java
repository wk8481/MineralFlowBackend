package be.kdg.prgramming6.landside.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        Appointment appointment = Appointment.scheduleAppointment(
                new Truck(licensePlate),
                materialType,
                windowStart,
                windowEnd,
                sellerId
        );

        addAppointment(appointment); // Add the appointment to the schedule

        // Output a message indicating the appointment has been created.
        // You can modify this to include a message based on your needs, like showing the truck's license plate
        System.out.println("Appointment created for truck with license plate: " + licensePlate);

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

    // Method to get all appointments
    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments); // Return a copy of the appointments list to maintain encapsulation
    }
}
