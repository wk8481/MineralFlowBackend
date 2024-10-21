// landside/src/main/java/be/kdg/prgramming6/landside/core/GetAllTrucksOnSiteUseCaseImpl.java
package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.port.in.GetAllTrucksOnSiteUseCase;
import be.kdg.prgramming6.landside.port.in.TruckResponse;
import be.kdg.prgramming6.landside.port.out.LoadTrucksByDaySchedulePort;
import be.kdg.prgramming6.landside.port.out.LoadWBTPort;
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
    public List<TruckResponse> getAllTrucksOnSite() {
        LocalDateTime now = LocalDateTime.now();
        return loadTrucksByDaySchedulePort.loadTrucksByDaySchedule(now).stream()
                .map(appointment -> appointment.getTruck().getLicensePlate().toString())
                .filter(licensePlate -> loadWBTPort.loadByLicensePlate(licensePlate).isPresent())
                .map(TruckResponse::new)
                .collect(Collectors.toList());
    }
}