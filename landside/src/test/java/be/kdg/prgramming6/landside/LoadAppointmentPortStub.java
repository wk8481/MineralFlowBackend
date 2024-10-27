// landside/src/test/java/be/kdg/prgramming6/landside/LoadAppointmentPortStub.java
package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.out.LoadAppointmentPort;

import java.util.List;

public class LoadAppointmentPortStub implements LoadAppointmentPort {
    private final List<Appointment> appointments;

    public LoadAppointmentPortStub(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public List<Appointment> loadAllAppointments() {
        return appointments;
    }
}