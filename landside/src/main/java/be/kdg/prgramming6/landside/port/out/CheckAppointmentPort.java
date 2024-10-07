package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.AppointmentId;

import java.util.UUID;

@FunctionalInterface
public interface CheckAppointmentPort {
    boolean existsById(AppointmentId appointmentId); // Method to check for appointment existence by ID
}
