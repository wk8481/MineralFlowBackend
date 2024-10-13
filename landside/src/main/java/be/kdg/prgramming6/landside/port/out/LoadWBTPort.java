package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;

import java.util.Optional;

@FunctionalInterface
public interface LoadWBTPort {
    Optional<WeighbridgeTicket> loadByLicensePlate(String licensePlate);
}
