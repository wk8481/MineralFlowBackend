package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.WarehouseId;

@FunctionalInterface
public interface ReceiveWarehouseNumberUseCase {
WarehouseId receiveWarehouseNumber(ReceiveWarehouseNumberCommand command);

}
