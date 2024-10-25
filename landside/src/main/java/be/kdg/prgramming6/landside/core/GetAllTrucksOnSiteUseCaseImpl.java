package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.port.in.GetAllTrucksOnSiteUseCase;
import be.kdg.prgramming6.landside.port.in.TruckResponse;
import be.kdg.prgramming6.landside.port.out.LoadTrucksByDaySchedulePort;
import be.kdg.prgramming6.landside.port.out.LoadWBTPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllTrucksOnSiteUseCaseImpl implements GetAllTrucksOnSiteUseCase {
    private final LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort;
    private final LoadWBTPort loadWBTPort;

    public GetAllTrucksOnSiteUseCaseImpl(LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort, LoadWBTPort loadWBTPort) {
        this.loadTrucksByDaySchedulePort = loadTrucksByDaySchedulePort;
        this.loadWBTPort = loadWBTPort;
    }

    @Override
    @Transactional
    public List<TruckResponse> getAllTrucksOnSite() {
        LocalDateTime scheduleTime = LocalDateTime.of(2024, 10, 30, 10, 0);
        return loadTrucksByDaySchedulePort.loadTrucksByDaySchedule(scheduleTime).stream()
                .map(appointment -> appointment.getTruck().getLicensePlate().toString())
                .filter(licensePlate -> loadWBTPort.loadByLicensePlate(licensePlate).isPresent())
                .map(TruckResponse::new)
                .collect(Collectors.toList());
    }
}