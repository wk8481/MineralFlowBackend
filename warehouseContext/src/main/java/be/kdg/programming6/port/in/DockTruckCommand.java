package be.kdg.programming6.port.in;

import be.kdg.programming6.domain.LicensePlate;
import be.kdg.programming6.domain.MaterialType;
import be.kdg.programming6.domain.WarehouseId;
import java.time.LocalDateTime;
import java.util.Objects;

public record DockTruckCommand(
        LicensePlate licensePlate,        // Truck's license plate
        MaterialType materialType,        // Type of material being delivered
        WarehouseId warehouseId,          // ID of the assigned warehouse
        String conveyorBeltId,            // ID of the conveyor belt for docking
        String weighingBridgeNumber,      // New weighing bridge number assigned
        LocalDateTime deliveryDate         // Date of delivery for the PDT
) {
    public DockTruckCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(materialType, "Material type cannot be null");
        Objects.requireNonNull(warehouseId, "Warehouse ID cannot be null");
        Objects.requireNonNull(conveyorBeltId, "Conveyor belt ID cannot be null");
        Objects.requireNonNull(weighingBridgeNumber, "Weighing bridge number cannot be null");
        Objects.requireNonNull(deliveryDate, "Delivery date cannot be null");
    }
}
