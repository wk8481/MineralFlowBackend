package be.kdg.prgramming6.warehouse.port.in;

@FunctionalInterface
public interface DockTruckUseCase {
    void dockTruck(DockTruckCommand command);
}
