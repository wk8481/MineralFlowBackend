package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@FunctionalInterface
public interface LoadDaySchedulePort {
   Optional<Schedule> loadDaySchedule(LocalDateTime scheduleTime);
}
