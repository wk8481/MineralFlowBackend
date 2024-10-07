package be.kdg.prgramming6.warehouse.domain;

import java.util.Objects;

public record LicensePlate(String plateNumber) {
    public LicensePlate {
        Objects.requireNonNull(plateNumber, "Plate number cannot be null");
        if (plateNumber.isEmpty()) {
            throw new IllegalArgumentException("Plate number cannot be empty");
        }
        // Additional validation can be added here if needed
    }
}
