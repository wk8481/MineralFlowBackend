package be.kdg.prgramming6.landside.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Truck {
    private final LicensePlate licensePlate;
    private static final BigDecimal TARE_WEIGHT = new BigDecimal("10000"); // Example constant tare weight
    private MaterialType materialType;
    private String dockNumber; // Conveyor belt/dock number
    private final SellerId sellerId; // Added seller field

    public Truck(LicensePlate licensePlate) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.sellerId = null;
    }

    public Truck(LicensePlate licensePlate, MaterialType materialType, String dockNumber) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.dockNumber = dockNumber; // Can be null initially
        this.sellerId = null; // Seller ID is not required for this constructor
    }

    public Truck(LicensePlate licensePlate, MaterialType materialType, String dockNumber, SellerId sellerId) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.dockNumber = dockNumber; // Can be null initially
        this.sellerId = Objects.requireNonNull(sellerId, "Seller ID cannot be null");
    }

    public Truck(LicensePlate licensePlate, MaterialType materialType, String dockNumber, UUID sellerId) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.dockNumber = dockNumber; // Can be null initially
        this.sellerId = new SellerId(sellerId);
    }

    // Business logic methods
    public boolean isValidLicensePlate() {
        return !licensePlate.toString().isEmpty();
    }

    public boolean canDock() {
        return dockNumber != null && !dockNumber.isEmpty();
    }

    public boolean matches(Appointment appointment) {
        return this.licensePlate.equals(appointment.getTruck().getLicensePlate()) &&
                this.materialType == appointment.getMaterialType();
    }

    // Getters
    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    public SellerId getSellerId() {
        return sellerId;
    }

    public BigDecimal getTareWeight() {
        return TARE_WEIGHT;
    }

    // Update method for material type and dock number
    public Truck update(MaterialType newMaterialType, String newDockNumber) {
        if (newMaterialType != null) {
            this.materialType = newMaterialType;
        }
        if (newDockNumber != null) {
            this.dockNumber = newDockNumber;
        }
        return new Truck(this.licensePlate, this.materialType, this.dockNumber, this.sellerId);
    }

    // Optional setters if needed
    public void setMaterialType(MaterialType materialType) {
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null");
        }
        this.materialType = materialType;
    }

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }
}