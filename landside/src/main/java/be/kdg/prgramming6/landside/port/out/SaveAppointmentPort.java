// landside/src/main/java/be/kdg/prgramming6/landside/port/out/SaveAppointmentPort.java
package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Appointment;

public interface SaveAppointmentPort {
    void saveAppointment(Appointment appointment);
}