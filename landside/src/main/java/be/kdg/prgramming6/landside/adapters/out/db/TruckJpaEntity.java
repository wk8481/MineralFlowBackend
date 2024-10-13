package be.kdg.prgramming6.landside.adapters.out.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class TruckJpaEntity {
    @Id
    private String licensePlate;

    @Column(nullable = false)
    private String materialType;

    @Column(nullable = false)
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