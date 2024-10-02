package be.kdg.programming6.core;

import be.kdg.programming6.domain.*;
import be.kdg.programming6.port.in.DockTruckCommand;
import be.kdg.programming6.port.in.DockTruckUseCase;
import be.kdg.programming6.port.in.out.LoadPDTPort;
import be.kdg.programming6.port.in.out.LoadWarehousePort;
import be.kdg.programming6.port.in.out.SavePDTPort;
import be.kdg.programming6.port.in.out.UpdatePDTPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {
    private final List<Warehouse> warehouses; // List of warehouses to check
    private final LoadWarehousePort loadWarehousePort; // Port to load warehouse
    private final SavePDTPort savePDTPort; // Port to save the PDT
    private final UpdatePDTPort updatePDTPort; // Port to update the PDT
    private final LoadPDTPort loadPDTPort; // Port to load the PDT

    public DockTruckUseCaseImpl(List<Warehouse> warehouses, LoadWarehousePort loadWarehousePort, SavePDTPort savePDTPort, UpdatePDTPort updatePDTPort, LoadPDTPort loadPDTPort) {
        this.warehouses = warehouses; // Injected list of warehouses
        this.loadWarehousePort = loadWarehousePort; // Initialize the load warehouse port
        this.savePDTPort = savePDTPort; // Initialize the save PDT port
        this.updatePDTPort = updatePDTPort; // Initialize the update PDT port
        this.loadPDTPort = loadPDTPort; // Initialize the load PDT port
    }

    @Override
    public void dockTruck(DockTruckCommand command) {
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String dockNumber = command.dockNumber();
        LocalDateTime deliveryDate = command.deliveryDate();

        // Use a static Warehouse ID (as UUID)
        WarehouseId warehouseId = new WarehouseId(UUID.fromString("00000000-0000-0000-0000-000000000001")); // Replace with your desired static UUID

        // Find the corresponding warehouse in the list
        Warehouse targetWarehouse = warehouses.stream()
                .filter(warehouse -> warehouse.getId().equals(warehouseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found."));

        Truck truck = new Truck(licensePlate, materialType);
        truck.setDockNumber(dockNumber);

        // Load existing PDT if it exists
        Optional<PayloadDeliveryTicket> existingPDT = loadPDTPort.loadPDTByPlate(licensePlate);

        // Dock the truck regardless of capacity limitations
        targetWarehouse.dockTruck(truck);

        PayloadDeliveryTicket pdt;
        WeighbridgeNumber weighbridgeNumber;

        // If an existing PDT is found, update it; otherwise, create a new one
        if (existingPDT.isPresent()) {
            pdt = existingPDT.get();
            // Optionally update the PDT if needed (e.g., update its state or information)
            updatePDTPort.updatePDT(licensePlate.toString(), materialType.toString(), warehouseId.id(), dockNumber, deliveryDate);
        } else {
            // Generate a new PDT and handle weighbridge assignment
            pdt = targetWarehouse.generatePDT(truck, dockNumber, deliveryDate);
            weighbridgeNumber = targetWarehouse.assignWeighbridgeNumber(truck);

            // Save the new PDT using the SavePDTPort
            savePDTPort.savePDT(licensePlate, materialType, warehouseId, dockNumber, deliveryDate);

            // Print the PDT and weighbridge number
            printPDTAndWeighbridge(pdt, weighbridgeNumber);
            return; // Early exit since we already printed the summary
        }

        // Print the existing PDT information and assign a weighbridge number
        printPDTAndWeighbridge(pdt, weighbridgeNumber = targetWarehouse.assignWeighbridgeNumber(truck));
    }

    private void printPDTAndWeighbridge(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
        // Print the PDT and weighbridge number
        System.out.println("----- Docking Summary -----");
        System.out.println("Payload Delivery Ticket: " + pdt);
        System.out.println("Weighbridge Number: " + weighbridgeNumber);
        System.out.println("---------------------------");
    }
}










//package be.kdg.programming6.core;
//
//import be.kdg.programming6.domain.*;
//import be.kdg.programming6.port.in.DockTruckCommand;
//import be.kdg.programming6.port.in.DockTruckUseCase;
//import be.kdg.programming6.port.in.out.LoadPDTPort;
//import be.kdg.programming6.port.in.out.LoadWarehousePort;
//import be.kdg.programming6.port.in.out.SavePDTPort;
//import be.kdg.programming6.port.in.out.UpdatePDTPort;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class DockTruckUseCaseImpl implements DockTruckUseCase {
//    private final List<Warehouse> warehouses; // List of warehouses to check
//    private final LoadWarehousePort loadWarehousePort; // Port to load warehouse by seller ID
//    private final SavePDTPort savePDTPort; // Port to save the PDT
//    private final UpdatePDTPort updatePDTPort; // Port to update the PDT
//    private final LoadPDTPort loadPDTPort; // Port to load the PDT
//
//    public DockTruckUseCaseImpl(List<Warehouse> warehouses, LoadWarehousePort loadWarehousePort, SavePDTPort savePDTPort, UpdatePDTPort updatePDTPort, LoadPDTPort loadPDTPort) {
//        this.warehouses = warehouses; // Injected list of warehouses
//        this.loadWarehousePort = loadWarehousePort; // Initialize the load warehouse port
//        this.savePDTPort = savePDTPort; // Initialize the save PDT port
//        this.updatePDTPort = updatePDTPort; // Initialize the update PDT port
//        this.loadPDTPort = loadPDTPort; // Initialize the load PDT port
//    }
//
//    @Override
//    public void dockTruck(DockTruckCommand command) {
//        LicensePlate licensePlate = command.licensePlate();
//        MaterialType materialType = command.materialType();
//        String dockNumber = command.dockNumber();
//        LocalDateTime deliveryDate = command.deliveryDate();
//
//        // Attempt to load existing Warehouse ID based on seller ID
//        WarehouseId warehouseId = loadWarehousePort.findWarehouseIdbySellerId(sellerId);
//
//        // If the warehouse ID is not found, use a static Warehouse ID (as UUID)
//        if (warehouseId == null) {
//            warehouseId = new WarehouseId(UUID.fromString("00000000-0000-0000-0000-000000000001")); // Replace with your desired static UUID
//        }
//
//        // Find the corresponding warehouse in the list
//        WarehouseId finalWarehouseId = warehouseId;
//        Warehouse targetWarehouse = warehouses.stream()
//                .filter(warehouse -> warehouse.getId().equals(finalWarehouseId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for the given seller ID."));
//
//        Truck truck = new Truck(licensePlate, materialType);
//        truck.setDockNumber(dockNumber);
//
//        // Load existing PDT if it exists
//        Optional<PayloadDeliveryTicket> existingPDT = loadPDTPort.loadPDTByPlate(licensePlate);
//
//        // Dock the truck regardless of capacity limitations
//        targetWarehouse.dockTruck(truck);
//
//        PayloadDeliveryTicket pdt;
//        WeighbridgeNumber weighbridgeNumber;
//
//        // If an existing PDT is found, update it; otherwise, create a new one
//        if (existingPDT.isPresent()) {
//            pdt = existingPDT.get();
//            // Optionally update the PDT if needed (e.g., update its state or information)
//            updatePDTPort.updatePDT(licensePlate.toString(), materialType.toString(), warehouseId.id(), dockNumber, deliveryDate);
//        } else {
//            // Generate a new PDT and handle weighbridge assignment
//            pdt = targetWarehouse.generatePDT(truck, dockNumber, deliveryDate);
//            weighbridgeNumber = targetWarehouse.assignWeighbridgeNumber(truck);
//
//            // Save the new PDT using the SavePDTPort
//            savePDTPort.savePDT(licensePlate, materialType, warehouseId, dockNumber, deliveryDate);
//
//            // Print the PDT and weighbridge number
//            printPDTAndWeighbridge(pdt, weighbridgeNumber);
//
//            return; // Early exit since we already printed the summary
//        }
//
//        // Print the existing PDT information and assign a weighbridge number
//        printPDTAndWeighbridge(pdt, weighbridgeNumber = targetWarehouse.assignWeighbridgeNumber(truck));
//    }
//
//    private void printPDTAndWeighbridge(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
//        // Print the PDT and weighbridge number
//        System.out.println("----- Docking Summary -----");
//        System.out.println("Payload Delivery Ticket: " + pdt);
//        System.out.println("Weighbridge Number: " + weighbridgeNumber);
//        System.out.println("---------------------------");
//    }
//}
