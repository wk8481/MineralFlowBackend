package be.kdg.programming6.port.in;

@FunctionalInterface
public interface DockTruckUseCase {
    void dockTruck(DockTruckCommand command);
}
