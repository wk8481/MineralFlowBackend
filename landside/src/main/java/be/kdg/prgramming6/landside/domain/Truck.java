package be.kdg.prgramming6.landside.domain;


import java.util.Objects;

public class Truck {
    private final LicensePlate licensePlate;
    private MaterialType materialType;
    private String dockNumber; // Conveyor belt/dock number


    public Truck(LicensePlate licensePlate) {
        this.licensePlate = licensePlate;
    }



    // Constructor with LicensePlate, MaterialType, payloadWeight, dockNumber, and SellerId
    public Truck(LicensePlate licensePlate, MaterialType materialType, String dockNumber) {
        this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.dockNumber = dockNumber; // Can be null initially
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


    // Update method for payload weight, material type, and dock number
    public Truck update( MaterialType newMaterialType, String newDockNumber) {


        if (newMaterialType != null) {
            this.materialType = newMaterialType;
        }

        if (newDockNumber != null) {
            this.dockNumber = newDockNumber;
        }

        // Return a new instance with updated values (optional)
        return new Truck(this.licensePlate, this.materialType, this.dockNumber);
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
