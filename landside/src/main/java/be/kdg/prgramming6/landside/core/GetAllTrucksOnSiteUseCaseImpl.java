package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.GetAllTrucksOnSiteUseCase;
import be.kdg.prgramming6.landside.port.in.TruckResponse;
import be.kdg.prgramming6.landside.port.out.LoadTrucksByDaySchedulePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllTrucksOnSiteUseCaseImpl implements GetAllTrucksOnSiteUseCase {
    private final LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort;

    public GetAllTrucksOnSiteUseCaseImpl(LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort) {
        this.loadTrucksByDaySchedulePort = loadTrucksByDaySchedulePort;
    }

    @Override
    @Transactional
    public List<TruckResponse> getAllTrucksOnSite(List<LocalDateTime> scheduleTimes) {
        return loadTrucksByDaySchedulePort.loadTrucksByDaySchedules(scheduleTimes).stream()
                .filter(appointment -> scheduleTimes.stream().anyMatch(appointment::isOnSite))
                .map(appointment -> new TruckResponse(appointment.getTruck().getLicensePlate().toString()))
                .collect(Collectors.toList());
    }
}