package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.Capacity;

@FunctionalInterface
public interface DockTruckUseCase {
    Capacity dockTruck(DockTruckCommand command);
}
