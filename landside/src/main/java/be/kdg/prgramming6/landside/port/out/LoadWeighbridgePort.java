package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.Weighbridge;
import be.kdg.prgramming6.landside.domain.WeighbridgeNumber;

import java.util.Optional;

public interface LoadWeighbridgePort {
    Optional<Weighbridge> loadWeighbridge(WeighbridgeNumber weighbridgeNumber);

}
