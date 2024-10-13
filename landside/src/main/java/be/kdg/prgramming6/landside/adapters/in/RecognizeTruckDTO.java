package be.kdg.prgramming6.landside.adapters.in;

import jakarta.validation.constraints.NotBlank;

public class RecognizeTruckDTO {
    @NotBlank(message = "License plate is mandatory")
    private String licensePlate;

    @NotBlank(message = "Material type is mandatory")
    private String materialType;

    @NotBlank(message = "Dock number is mandatory")
    private String dockNumber;

    // Getters and setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }
}