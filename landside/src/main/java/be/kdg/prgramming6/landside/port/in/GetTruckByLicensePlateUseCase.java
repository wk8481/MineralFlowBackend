package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.Appointment;

@FunctionalInterface
public interface GetTruckByLicensePlateUseCase {
    Appointment getAppointmentByLicensePlate(String licensePlate);
}