package be.kdg.prgramming6.landside.adapters.out.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(catalog = "landside", name = "weighbridge")
public class WeighbridgeJpaEntity {
    @Id
    private String weighbridgeNumber;


    protected WeighbridgeJpaEntity() {
        // Required by JPA
    }

    public WeighbridgeJpaEntity(String weighbridgeNumber) {
        this.weighbridgeNumber = weighbridgeNumber;
    }

    public String getWeighbridgeNumber() {
        return weighbridgeNumber;
    }
}


