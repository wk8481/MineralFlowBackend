package be.kdg.prgramming6.port.out;

import be.kdg.prgramming6.domain.AppointmentId;

import java.util.UUID;

@FunctionalInterface
public interface CheckAppointmentPort {
    boolean existsById(AppointmentId appointmentId); // Method to check for appointment existence by ID
}
