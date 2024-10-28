package be.kdg.prgramming6.landside.port.in;

import java.time.LocalDateTime;
import java.util.List;

@FunctionalInterface
public interface GetAllTrucksOnSiteUseCase {
    List<TruckResponse> getAllTrucksOnSite(List<LocalDateTime> scheduleTimes);
}