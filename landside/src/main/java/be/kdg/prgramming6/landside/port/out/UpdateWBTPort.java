package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;

@FunctionalInterface
public interface UpdateWBTPort {
    void update(WeighbridgeTicket weighbridgeTicket, Truck truck);

    void update(WeighbridgeTicket weighbridgeTicket);
}