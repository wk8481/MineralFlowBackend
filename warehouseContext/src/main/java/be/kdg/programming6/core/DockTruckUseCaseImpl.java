package be.kdg.programming6.core;

import be.kdg.programming6.domain.*;
import be.kdg.programming6.port.in.DockTruckCommand;
import be.kdg.programming6.port.in.DockTruckUseCase;
import be.kdg.programming6.port.in.out.LoadWarehousePort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {
    private final List<Warehouse> warehouses; // List of warehouses to check
    private final LoadWarehousePort loadWarehousePort; // Port to load warehouse by seller ID

    public DockTruckUseCaseImpl(List<Warehouse> warehouses, LoadWarehousePort loadWarehousePort) {
        this.warehouses = warehouses; // Injected list of warehouses
        this.loadWarehousePort = loadWarehousePort; // Initialize the load warehouse port
    }

    @Override
    public void dockTruck(DockTruckCommand command) {
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String dockNumber = command.conveyorBeltId();
        LocalDateTime deliveryDate = command.deliveryDate();
        UUID sellerId = command.sellerId();

        // Attempt to load existing Warehouse ID based on seller ID
        WarehouseId warehouseId = loadWarehousePort.findWarehouseIdbySellerId(sellerId);

        // If the warehouse ID is not found, use a static Warehouse ID (as UUID)
        if (warehouseId == null) {
            warehouseId = new WarehouseId(UUID.fromString("00000000-0000-0000-0000-000000000001")); // Replace with your desired static UUID
        }

        // Find the corresponding warehouse in the list
        WarehouseId finalWarehouseId = warehouseId;
        Warehouse targetWarehouse = warehouses.stream()
                .filter(warehouse -> warehouse.getId().equals(finalWarehouseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for the given seller ID."));

        Truck truck = new Truck(licensePlate, materialType);
        truck.setDockNumber(dockNumber);

        if (targetWarehouse.canDockTruck()) {
            targetWarehouse.dockTruck(truck);

            // Generate the PDT and handle weighbridge assignment
            PayloadDeliveryTicket pdt = targetWarehouse.generatePDT(truck, dockNumber, deliveryDate);
            WeighbridgeNumber weighbridgeNumber = targetWarehouse.assignWeighbridgeNumber(truck);

            // Print the PDT and weighbridge number
            printPDTAndWeighbridge(pdt, weighbridgeNumber);
        } else {
            throw new IllegalArgumentException("Truck cannot dock at the warehouse due to capacity or restrictions.");
        }
    }



    private void printPDTAndWeighbridge(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
        // Print the PDT and weighbridge number
        System.out.println("----- Docking Summary -----");
        System.out.println("Payload Delivery Ticket: " + pdt);
        System.out.println("Weighbridge Number: " + weighbridgeNumber);
        System.out.println("---------------------------");
    }
}
