package be.kdg.prgramming6.landside.port.in;

import java.util.List;

@FunctionalInterface
public interface GetAllTrucksOnSiteUseCase {
    List<TruckResponse> getAllTrucksOnSite();
}