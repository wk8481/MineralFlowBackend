package be.kdg.prgramming6.landside.port.in;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record ReceiveWarehouseNumberCommand(String licensePlate, BigDecimal netWeight, String materialType, UUID sellerId) {
    public ReceiveWarehouseNumberCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(netWeight, "Net weight cannot be null");
        Objects.requireNonNull(materialType, "Material type cannot be null");
        Objects.requireNonNull(sellerId, "Seller ID cannot be null");
    }
}