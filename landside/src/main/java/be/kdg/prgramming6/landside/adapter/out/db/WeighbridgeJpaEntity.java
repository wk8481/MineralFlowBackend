package be.kdg.prgramming6.landside.adapter.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(catalog = "landside", name = "weighbridge")
public class WeighbridgeJpaEntity {
    @Id
    @Column(name = "weighbridge_number")
    private String weighbridgeNumber;

    @Column(name = "license_plate")
    private String licensePlate;

    protected WeighbridgeJpaEntity() {
        // Required by JPA
    }

    public WeighbridgeJpaEntity(String weighbridgeNumber, String licensePlate) {
        this.weighbridgeNumber = weighbridgeNumber;
        this.licensePlate = licensePlate;
    }

    public String getWeighbridgeNumber() {
        return weighbridgeNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}