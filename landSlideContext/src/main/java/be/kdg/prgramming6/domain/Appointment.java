package be.kdg.prgramming6.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record Appointment(UUID appointmentId, Truck truck, MaterialType materialType, LocalDateTime windowStart,
                          LocalDateTime windowEnd, SellerId sellerId) {
    public Appointment {
        if (appointmentId == null || truck == null || materialType == null ||
                windowStart == null || windowEnd == null || sellerId == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (windowStart.isAfter(windowEnd)) {
            throw new IllegalArgumentException("Window start cannot be after window end");
        }
        if (windowStart.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }

    }

    // Method to check if this appointment overlaps with another time window
    public boolean overlapsWith(LocalDateTime start, LocalDateTime end) {
        return (start.isBefore(windowEnd) && end.isAfter(windowStart));
    }

    // Method to schedule an appointment
    public static Appointment scheduleAppointment(UUID appointmentId, Truck truck,
                                                  MaterialType materialType, LocalDateTime windowStart,
                                                  LocalDateTime windowEnd, SellerId sellerId) {
        return new Appointment(appointmentId, truck, materialType, windowStart, windowEnd, sellerId);
    }

    // Getter methods...
}
