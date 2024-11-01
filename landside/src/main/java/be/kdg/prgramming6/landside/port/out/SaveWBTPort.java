package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;

@FunctionalInterface
public interface SaveWBTPort {
    void save(WeighbridgeTicket weighbridgeTicket);
}
