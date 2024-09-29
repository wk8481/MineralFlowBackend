package be.kdg.prgramming6.port.out;

import be.kdg.prgramming6.domain.Appointment;

public interface CreateAppointmentPort {
    void createAppointment(Appointment appointment);
}
