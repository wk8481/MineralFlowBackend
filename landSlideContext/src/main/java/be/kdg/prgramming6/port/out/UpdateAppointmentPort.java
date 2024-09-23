package be.kdg.prgramming6.port.out;

import be.kdg.prgramming6.domain.Appointment;

@FunctionalInterface
public interface UpdateAppointmentPort {
    void updateAppointment(Appointment appointment);
}
