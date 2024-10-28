// landside/src/test/java/be/kdg/prgramming6/landside/LoadTrucksByDaySchedulePortStub.java
package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.out.LoadTrucksByDaySchedulePort;

import java.time.LocalDateTime;
import java.util.List;

public class LoadTrucksByDaySchedulePortStub implements LoadTrucksByDaySchedulePort {
    private final List<Appointment> appointments;

    public LoadTrucksByDaySchedulePortStub(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public List<Appointment> loadTrucksByDaySchedules(List<LocalDateTime> scheduleTimes) {
        return appointments;
    }
}