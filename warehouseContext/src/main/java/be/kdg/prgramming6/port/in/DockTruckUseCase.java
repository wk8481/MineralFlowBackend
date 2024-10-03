package be.kdg.prgramming6.port.in;

@FunctionalInterface
public interface DockTruckUseCase {
    void dockTruck(DockTruckCommand command);
}
