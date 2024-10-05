package be.kdg.prgramming6.warehouse.domain;

public class Truck {
    private final LicensePlate licensePlate;
    private final MaterialType materialType;
    private String dockNumber;

    public Truck(LicensePlate licensePlate, MaterialType materialType) {
        this.licensePlate = licensePlate;
        this.materialType = materialType;
    }

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    // Other methods...
}
