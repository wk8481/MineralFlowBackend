package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.domain.Appointment;

public class UpdateAppointmentPortStub {
    private Appointment appointment;

    public void updateAppointment(final Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
