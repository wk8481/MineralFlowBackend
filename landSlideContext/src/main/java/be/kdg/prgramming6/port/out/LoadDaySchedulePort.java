package be.kdg.prgramming6.port.out;

import be.kdg.prgramming6.domain.Schedule;

import java.time.LocalDate;
import java.util.Optional;

@FunctionalInterface
public interface LoadDaySchedulePort {
   Optional<Schedule> loadDaySchedule(LocalDate day);
}
