package be.kdg.programming6.core;

import be.kdg.programming6.domain.*;
import be.kdg.programming6.port.in.DockTruckCommand;
import be.kdg.programming6.port.in.DockTruckUseCase;

import java.time.LocalDateTime;

public class DockTruckUseCaseImpl implements DockTruckUseCase {
    private final Warehouse warehouse;

    public DockTruckUseCaseImpl(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void dockTruck(DockTruckCommand command) {
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String dockNumber = command.conveyorBeltId(); // Renamed for clarity
        LocalDateTime deliveryDate = command.deliveryDate();

        // Create a Truck instance
        Truck truck = new Truck(licensePlate, materialType);
        truck.setDockNumber(dockNumber);

        // Check if the truck can dock
        if (!warehouse.canDockTruck()) {
            throw new IllegalArgumentException("Truck cannot dock at this warehouse due to capacity or restrictions.");
        }

        // Dock the truck
        warehouse.dockTruck(truck);

        // Generate the weighbridge number
        WeighbridgeNumber weighbridgeNumber = warehouse.assignWeighbridgeNumber(truck);

        // Generate the PDT using the dock number and delivery date
        PayloadDeliveryTicket pdt = warehouse.generatePDT(truck, dockNumber, deliveryDate);

        // Save the PDT and weighbridge number as needed
        savePDT(pdt, weighbridgeNumber);
    }

    private void savePDT(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
        // Logic to save the PDT and weighbridge number to a database
        // This could involve a repository pattern or direct database interaction
    }
}
