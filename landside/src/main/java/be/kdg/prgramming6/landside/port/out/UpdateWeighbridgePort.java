package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Weighbridge;

@FunctionalInterface
public interface UpdateWeighbridgePort {
    void updateWeighbridge(Weighbridge weighbridge);
}
