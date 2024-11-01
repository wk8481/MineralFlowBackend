package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Appointment;

import java.util.Optional;

@FunctionalInterface
public interface LoadAppointmentByLicensePlatePort {
    Optional<Appointment> loadAppointmentByLicensePlate(String licensePlate);
}