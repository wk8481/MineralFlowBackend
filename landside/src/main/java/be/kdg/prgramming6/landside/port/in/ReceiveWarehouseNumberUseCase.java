package be.kdg.prgramming6.landside.port.in;



@FunctionalInterface
public interface ReceiveWarehouseNumberUseCase {
ReceiveWarehouseNumberResponse receiveWarehouseNumber(ReceiveWarehouseNumberCommand command);

}
