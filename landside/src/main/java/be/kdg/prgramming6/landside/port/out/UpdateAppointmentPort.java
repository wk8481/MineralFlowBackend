package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Appointment;

@FunctionalInterface
public interface UpdateAppointmentPort {
    void updateAppointment(Appointment appointment);
}
