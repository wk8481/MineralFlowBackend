package be.kdg.programming6.core;

import be.kdg.programming6.domain.*;
import be.kdg.programming6.port.in.DockTruckCommand;
import be.kdg.programming6.port.in.DockTruckUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DockTruckUseCaseImpl implements DockTruckUseCase {

    private final Warehouse warehouse; // Assuming you want a single warehouse instance

    // Constructor to initialize Warehouse with required parameters
    public DockTruckUseCaseImpl(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void dockTruck(DockTruckCommand command) {
        // Retrieve the values from the command
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String conveyorBeltId = command.conveyorBeltId();
        String weighingBridgeNumber = command.weighingBridgeNumber();
        LocalDateTime deliveryDate = command.deliveryDate();

        // Create a Truck instance
        Truck truck = new Truck(licensePlate, materialType);

        // Perform the docking logic
        if (!warehouse.canDockTruck(truck)) {
            throw new IllegalArgumentException("Truck cannot dock at this warehouse due to business rules.");
        }

        // Dock the truck
        warehouse.dockTruck(truck);

        // Generate the PDT
        PayloadDeliveryTicket pdt = warehouse.generatePDT(truck, conveyorBeltId, weighingBridgeNumber, deliveryDate);

        // You may save the PDT to a database or perform additional operations here
        savePDT(pdt); // Example method to handle the saving
    }

    private void savePDT(PayloadDeliveryTicket pdt) {
        // Logic to save the PDT to a database
    }
}
