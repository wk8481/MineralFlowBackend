package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.Appointment;

@FunctionalInterface
public interface GetTruckByIdUseCase {
    Appointment getAppointmentById(int appointmentId);
}
