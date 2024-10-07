package be.kdg.prgramming6.landside.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Truck {
    private final LicensePlate licensePlate;
    private MaterialType materialType;
    private BigDecimal payloadWeight;

    // Constructor with just LicensePlate
    public Truck(LicensePlate licensePlate) {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
        this.licensePlate = licensePlate;
    }

    // Constructor with LicensePlate, MaterialType, and payloadWeight
    public Truck(LicensePlate licensePlate, BigDecimal payloadWeight, MaterialType materialType) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.payloadWeight = Objects.requireNonNull(payloadWeight, "Payload weight cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
    }

    // Getters
    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public BigDecimal getPayloadWeight() {
        return payloadWeight;
    }

    // Optional setters if needed (immutable by default)
    public void setMaterialType(MaterialType materialType) {
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null");
        }
        this.materialType = materialType;
    }

    public void setPayloadWeight(BigDecimal payloadWeight) {
        if (payloadWeight == null || payloadWeight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payload weight must be positive");
        }
        this.payloadWeight = payloadWeight;
    }
}
