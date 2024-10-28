package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface LoadTrucksByDaySchedulePort {
    List<Appointment> loadTrucksByDaySchedules(List<LocalDateTime> scheduleTimes);
}