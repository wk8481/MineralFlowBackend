package be.kdg.prgramming6.landside.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Truck {
    private final LicensePlate licensePlate;
    private MaterialType materialType;
    private BigDecimal payloadWeight;
    private String dockNumber; // Conveyor belt/dock number
    private final SellerId sellerId; // Seller ID associated with the truck

    // Constructor with just LicensePlate
    public Truck(LicensePlate licensePlate, SellerId sellerId) {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
        if (sellerId == null) {
            throw new IllegalArgumentException("Seller ID cannot be null");
        }
        this.licensePlate = licensePlate;
        this.sellerId = sellerId;
    }

    // Constructor with LicensePlate, MaterialType, payloadWeight, dockNumber, and SellerId
    public Truck(LicensePlate licensePlate, BigDecimal payloadWeight, MaterialType materialType, String dockNumber, SellerId sellerId) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.payloadWeight = Objects.requireNonNull(payloadWeight, "Payload weight cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.dockNumber = dockNumber; // Can be null initially
        this.sellerId = Objects.requireNonNull(sellerId, "Seller ID cannot be null");
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

    public String getDockNumber() {
        return dockNumber;
    }

    public SellerId getSellerId() {
        return sellerId;
    }

    // Update method for payload weight, material type, and dock number
    public Truck update(BigDecimal newPayloadWeight, MaterialType newMaterialType, String newDockNumber) {
        if (newPayloadWeight != null) {
            if (newPayloadWeight.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Payload weight must be positive");
            }
            this.payloadWeight = newPayloadWeight;
        }

        if (newMaterialType != null) {
            this.materialType = newMaterialType;
        }

        if (newDockNumber != null) {
            this.dockNumber = newDockNumber;
        }

        // Return a new instance with updated values (optional)
        return new Truck(this.licensePlate, this.payloadWeight, this.materialType, this.dockNumber, this.sellerId);
    }

    // Optional setters if needed
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

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }
}
