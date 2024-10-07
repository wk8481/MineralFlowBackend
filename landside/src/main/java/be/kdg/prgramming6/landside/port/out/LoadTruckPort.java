package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.Truck;


import java.util.Optional;

@FunctionalInterface
public interface LoadTruckPort {
    Optional<Truck> loadTruck(LicensePlate licensePlate);
}
