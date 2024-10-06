package be.kdg.prgramming6.domain;

import java.util.UUID;

public record AppointmentId(UUID appointmentId) {
    public AppointmentId {
        if (appointmentId == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
    }
}
