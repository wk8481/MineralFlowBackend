package be.kdg.prgramming6.landside.port.in;

import java.util.Objects;
import java.util.UUID;

public record ReceiveWarehouseNumberCommand(String licensePlate, UUID sellerId, String materialType) {
    public ReceiveWarehouseNumberCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(sellerId, "Seller ID cannot be null");
        Objects.requireNonNull(materialType, "Material type cannot be null");
    }
}