package be.kdg.programming6.domain;

public class Truck {
    private final LicensePlate licensePlate;
    private final MaterialType materialType;
    private String dockNumber; // Add dock number to track docking location

    // Constructor
    public Truck(LicensePlate licensePlate, MaterialType materialType) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.dockNumber = null; // Initially, no dock number assigned
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

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "licensePlate=" + licensePlate +
                ", materialType=" + materialType +
                ", dockNumber='" + dockNumber + '\'' +
                '}';
    }
}
