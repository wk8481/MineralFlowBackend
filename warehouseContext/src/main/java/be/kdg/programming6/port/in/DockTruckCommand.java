package be.kdg.programming6.port.in;

import be.kdg.programming6.domain.LicensePlate;
import be.kdg.programming6.domain.MaterialType;
import be.kdg.programming6.domain.WarehouseId;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record DockTruckCommand(
        LicensePlate licensePlate,               // Truck's license plate
        MaterialType materialType,               // Type of material being delivered
        WarehouseId warehouseId,                 // ID of the assigned warehouse
        String conveyorBeltId,                   // ID of the conveyor belt for docking
        LocalDateTime deliveryDate,                // Date of delivery for the PDT
        UUID sellerId                            // ID of the seller
) {
    public DockTruckCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(materialType, "Material type cannot be null");
        Objects.requireNonNull(warehouseId, "Warehouse ID cannot be null");
        Objects.requireNonNull(conveyorBeltId, "Conveyor belt ID cannot be null");
        Objects.requireNonNull(deliveryDate, "Delivery date cannot be null");
        Objects.requireNonNull(sellerId, "Seller ID cannot be null");
    }
}
