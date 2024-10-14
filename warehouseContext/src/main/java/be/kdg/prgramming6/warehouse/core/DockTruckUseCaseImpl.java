package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.DockTruckCommand;
import be.kdg.prgramming6.warehouse.port.in.DockTruckUseCase;
import be.kdg.prgramming6.warehouse.port.in.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {
    private static final Logger logger = LoggerFactory.getLogger(DockTruckUseCaseImpl.class);

    private final LoadWarehouseBySellerAndMaterialPort loadWarehouseBySellerAndMaterialPort;
    private final LoadWarehouseByIdPort loadWarehouseByIdPort;
    private final SavePDTPort savePDTPort;
    private final UpdatePDTPort updatePDTPort;
    private final LoadPDTPort loadPDTPort;
    private final List<UpdateWarehousePort> updateWarehousePorts;

    public DockTruckUseCaseImpl(
            LoadWarehouseBySellerAndMaterialPort loadWarehouseBySellerAndMaterialPort,
            LoadWarehouseByIdPort loadWarehouseByIdPort,
            SavePDTPort savePDTPort,
            UpdatePDTPort updatePDTPort,
            LoadPDTPort loadPDTPort,
            List<UpdateWarehousePort> updateWarehousePorts) {
        this.loadWarehouseBySellerAndMaterialPort = loadWarehouseBySellerAndMaterialPort;
        this.loadWarehouseByIdPort = loadWarehouseByIdPort;
        this.savePDTPort = savePDTPort;
        this.updatePDTPort = updatePDTPort;
        this.loadPDTPort = loadPDTPort;
        this.updateWarehousePorts = updateWarehousePorts;
    }

    @Override
    @Transactional
    public void dockTruck(final DockTruckCommand command) {
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String dockNumber = command.dockNumber();
        LocalDateTime deliveryDate = command.deliveryDate();
        UUID sellerId = command.sellerId();
        BigDecimal weight = command.weight();

        logger.debug("Starting docking process for Truck with License Plate: {}", licensePlate);
        logger.debug("Command Details - Material Type: {}, Dock Number: {}, Delivery Date: {}, Seller ID: {}, Weight: {}",
                materialType, dockNumber, deliveryDate, sellerId, weight);

        WarehouseId warehouseId = loadWarehouseBySellerAndMaterialPort.findWarehouseIdBySellerIdAndMaterialType(sellerId, materialType);
        if (warehouseId == null) {
            logger.error("No warehouse found for seller with ID: {} and material type: {}", sellerId, materialType);
            throw new IllegalArgumentException("No warehouse found for seller with ID: " + sellerId);
        }

        logger.debug("Found Warehouse ID: {}", warehouseId);

        Truck truck = new Truck(licensePlate, materialType);
        truck.setDockNumber(dockNumber);

        // Load existing PDT
        Optional<PayloadDeliveryTicket> existingPDT = loadPDTPort.loadPDTByPlate(licensePlate);
        WeighbridgeNumber weighbridgeNumber = WeighbridgeNumber.generate(truck);

        if (existingPDT.isPresent()) {
            handleExistingPDT(existingPDT.get(), command, warehouseId, weighbridgeNumber);
        } else {
            createAndSaveNewPDT(licensePlate, materialType, deliveryDate, warehouseId, dockNumber, weighbridgeNumber);
        }

        // Update warehouse capacity after docking
        updateWarehouseCapacity(warehouseId, weight);
    }

    private void printPDTAndWeighbridge(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
        logger.info("----- Docking Summary -----");
        logger.info("Payload Delivery Ticket: {}", pdt);
        logger.info("Weighbridge Number: {}", weighbridgeNumber);
        logger.info("---------------------------");
    }

    private void handleExistingPDT(PayloadDeliveryTicket pdt, DockTruckCommand command, WarehouseId warehouseId, WeighbridgeNumber weighbridgeNumber) {
        logger.debug("Found existing PDT: {}", pdt);

        // Update the existing PDT
        updatePDTPort.updatePDT(pdt.getLicensePlate().toString(), pdt.getMaterialType().toString(), warehouseId.id(), command.dockNumber(), command.deliveryDate());
        logger.info("Updated existing PDT for License Plate: {}", pdt.getLicensePlate());
        printPDTAndWeighbridge(pdt, weighbridgeNumber);
    }

    private void createAndSaveNewPDT(LicensePlate licensePlate, MaterialType materialType, LocalDateTime deliveryDate, WarehouseId warehouseId, String dockNumber, WeighbridgeNumber weighbridgeNumber) {
        PayloadDeliveryTicket newPDT = new PayloadDeliveryTicket(licensePlate, materialType, deliveryDate, warehouseId, dockNumber);

        // Save the newly created PDT
        savePDTPort.savePDT(licensePlate, materialType, warehouseId, dockNumber, deliveryDate);
        logger.info("Created and saved new PDT: {}", newPDT);
        printPDTAndWeighbridge(newPDT, weighbridgeNumber);
    }

    private void updateWarehouseCapacity(WarehouseId warehouseId, BigDecimal weight) {
        Optional<Warehouse> optionalWarehouse = loadWarehouseByIdPort.loadWarehouseById(warehouseId);
        Warehouse warehouse = optionalWarehouse.orElseThrow(() -> new IllegalArgumentException("Warehouse not found for ID: " + warehouseId));

        // Add the activity with the given weight
        final WarehouseActivity warehouseActivity = warehouse.addActivity(weight);
        updateWarehousePorts.forEach(updateWarehousePort -> updateWarehousePort.activityCreated(warehouse, warehouseActivity));
    }
}