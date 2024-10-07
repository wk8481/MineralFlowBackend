package be.kdg.prgramming6.warehouse.core;


import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.DockTruckCommand;
import be.kdg.prgramming6.warehouse.port.in.DockTruckUseCase;
import be.kdg.prgramming6.warehouse.port.in.out.LoadPDTPort;
import be.kdg.prgramming6.warehouse.port.in.out.LoadWarehousePort;
import be.kdg.prgramming6.warehouse.port.in.out.SavePDTPort;
import be.kdg.prgramming6.warehouse.port.in.out.UpdatePDTPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {
    private static final Logger logger = LoggerFactory.getLogger(DockTruckUseCaseImpl.class);

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
    @Transactional
    public void dockTruck(DockTruckCommand command) {
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String dockNumber = command.dockNumber();
        LocalDateTime deliveryDate = command.deliveryDate();
        UUID sellerId = command.sellerId();

        logger.debug("Starting docking process for Truck with License Plate: {}", licensePlate);
        logger.debug("Command Details - Material Type: {}, Dock Number: {}, Delivery Date: {}, Seller ID: {}",
                materialType, dockNumber, deliveryDate, sellerId);

        // Find the corresponding WarehouseId using sellerId and materialType
        WarehouseId warehouseId = loadWarehousePort.findWarehouseIdBySellerIdAndMaterialType(sellerId, materialType);
        if (warehouseId == null) {
            logger.error("No warehouse found for seller with ID: {} and material type: {}", sellerId, materialType);
            throw new IllegalArgumentException("No warehouse found for seller with ID: " + sellerId);
        }

        logger.debug("Found Warehouse ID: {}", warehouseId);

        // Create truck
        Truck truck = new Truck(licensePlate, materialType);
        truck.setDockNumber(dockNumber);

        // Load existing PDT
        Optional<PayloadDeliveryTicket> existingPDT = loadPDTPort.loadPDTByPlate(licensePlate);

        // Generate a unique weighbridge number using the Truck object
        WeighbridgeNumber weighbridgeNumber = WeighbridgeNumber.generate(truck);

        if (existingPDT.isPresent()) {
            PayloadDeliveryTicket pdt = existingPDT.get();
            logger.debug("Found existing PDT: {}", pdt);

            // Update the existing PDT using UpdatePDTPort
            updatePDTPort.updatePDT(licensePlate.toString(), materialType.toString(), warehouseId.id(), dockNumber, deliveryDate);
            logger.info("Updated existing PDT for License Plate: {}", licensePlate);
            printPDTAndWeighbridge(pdt, weighbridgeNumber); // Print updated PDT information
        } else {
            // Create a new PDT with the required information
            PayloadDeliveryTicket newPDT = new PayloadDeliveryTicket(licensePlate, materialType, deliveryDate, warehouseId, dockNumber);

            // Save the newly created PDT using SavePDTPort
            savePDTPort.savePDT(licensePlate, materialType, warehouseId, dockNumber, deliveryDate);
            logger.info("Created and saved new PDT: {}", newPDT);
            printPDTAndWeighbridge(newPDT, weighbridgeNumber); // Print created PDT information
        }
    }

    private void printPDTAndWeighbridge(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
        // Print the PDT and weighbridge number
        logger.info("----- Docking Summary -----");
        logger.info("Payload Delivery Ticket: {}", pdt);
        logger.info("Weighbridge Number: {}", weighbridgeNumber);
        logger.info("---------------------------");
    }
}
