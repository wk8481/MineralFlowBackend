package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;

@FunctionalInterface
public interface CheckArrivalTruckUseCase {
    List<Appointment> loadAllAppointments(List<LocalDateTime> scheduleTimes);
}