package be.kdg.prgramming6.landside.port.in;

import java.util.Objects;

public record RecognizeTruckCommand(String licensePlate, String materialType, String dockNumber) {
    public RecognizeTruckCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        if (licensePlate.isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be empty");
        }
        Objects.requireNonNull(materialType, "Material type cannot be null");
        Objects.requireNonNull(dockNumber, "Dock number cannot be null");
    }
}