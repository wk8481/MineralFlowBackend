package be.kdg.prgramming6.warehouse.port.in;

@FunctionalInterface
public interface UpdateWarehouseCapacityUseCase {
    void updateWarehouseCapacity(UpdateWarehouseCapacityCommand command);
}
