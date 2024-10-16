package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.LicensePlate;
import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record DockTruckCommand(
        LicensePlate licensePlate,               // Truck's license plate
        MaterialType materialType,               // Type of material being delivered
        WarehouseId warehouseId,                 // ID of the assigned warehouse
        String dockNumber,                   // ID of the conveyor belt for docking
        LocalDateTime deliveryDate,               // Date of delivery for the PDT
        UUID sellerId                            // ID of the seller
        // Weight of the material being delivered
) {
    public DockTruckCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(materialType, "Material type cannot be null");
        Objects.requireNonNull(warehouseId, "Warehouse ID cannot be null");
        Objects.requireNonNull(dockNumber, "Conveyor belt ID cannot be null");
        Objects.requireNonNull(deliveryDate, "Delivery date cannot be null");
        Objects.requireNonNull(sellerId, "Seller ID cannot be null");

    }
}
