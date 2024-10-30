package be.kdg.prgramming6.warehouse.core;

import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.DockTruckCommand;
import be.kdg.prgramming6.warehouse.port.in.DockTruckUseCase;
import be.kdg.prgramming6.warehouse.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {
    private static final Logger logger = LoggerFactory.getLogger(DockTruckUseCaseImpl.class);

    private final LoadWarehouseBySellerAndMaterialPort loadWarehouseBySellerAndMaterialPort;
    private final SavePDTPort savePDTPort;
    private final UpdatePDTPort updatePDTPort;
    private final LoadPDTPort loadPDTPort;
    private final LoadWarehouseByIdPort loadWarehouseByIdPort;

    public DockTruckUseCaseImpl(
            LoadWarehouseBySellerAndMaterialPort loadWarehouseBySellerAndMaterialPort,
            SavePDTPort savePDTPort,
            UpdatePDTPort updatePDTPort,
            LoadPDTPort loadPDTPort, LoadWarehouseByIdPort loadWarehouseByIdPort) {
        this.loadWarehouseBySellerAndMaterialPort = loadWarehouseBySellerAndMaterialPort;
        this.savePDTPort = savePDTPort;
        this.updatePDTPort = updatePDTPort;
        this.loadPDTPort = loadPDTPort;
        this.loadWarehouseByIdPort = loadWarehouseByIdPort;
    }

    @Override
    @Transactional
    public void dockTruck(final DockTruckCommand command) {
        LicensePlate licensePlate = command.licensePlate();
        MaterialType materialType = command.materialType();
        String dockNumber = command.dockNumber();
        LocalDateTime deliveryDate = command.deliveryDate();
        UUID sellerId = command.sellerId();

        logger.debug("Starting docking process for Truck with License Plate: {}", licensePlate);
        logger.debug("Command Details - Material Type: {}, Dock Number: {}, Delivery Date: {}, Seller ID: {}",
                materialType, dockNumber, deliveryDate, sellerId);

        WarehouseId warehouseId = loadWarehouseBySellerAndMaterialPort.findWarehouseIdBySellerIdAndMaterialType(sellerId, materialType);
        if (warehouseId == null) {
            logger.error("No warehouse found for seller with ID: {} and material type: {}", sellerId, materialType);
            throw new IllegalArgumentException("No warehouse found for seller with ID: " + sellerId);
        }

        logger.debug("Found Warehouse ID: {}", warehouseId);

        Truck truck = new Truck(licensePlate, materialType);
        truck.setDockNumber(dockNumber);

        Optional<PayloadDeliveryTicket> existingPDT = loadPDTPort.loadPDTByPlate(licensePlate);
        WeighbridgeNumber weighbridgeNumber = WeighbridgeNumber.generate(truck);

        Warehouse warehouse = loadWarehouseByIdPort.loadWarehouseById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for ID: " + warehouseId));

        if (existingPDT.isPresent()) {
            warehouse.updatePDT(existingPDT.get(), dockNumber, deliveryDate);
            updatePDTPort.updatePDT(existingPDT.get().getLicensePlate().toString(), materialType.toString(), warehouseId.id(), dockNumber, deliveryDate);
            logger.info("Updated existing PDT for License Plate: {}", existingPDT.get().getLicensePlate());
        } else {
            PayloadDeliveryTicket newPDT = warehouse.generatePDT(truck, dockNumber, deliveryDate);
            savePDTPort.savePDT(licensePlate, materialType, warehouseId, dockNumber, deliveryDate);
            logger.info("Created and saved new PDT: {}", newPDT);
        }

        printPDTAndWeighbridge(existingPDT.orElse(null), weighbridgeNumber);
    }

    private void printPDTAndWeighbridge(PayloadDeliveryTicket pdt, WeighbridgeNumber weighbridgeNumber) {
        logger.info("----- Docking Summary -----");
        if (pdt != null) {
            logger.info("Payload Delivery Ticket: {}", pdt);
        }
        logger.info("Weighbridge Number: {}", weighbridgeNumber);
        logger.info("---------------------------");
    }
}