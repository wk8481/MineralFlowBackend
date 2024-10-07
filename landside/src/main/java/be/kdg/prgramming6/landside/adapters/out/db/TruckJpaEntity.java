package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.MaterialType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(catalog="landside", name = "truck")
public class TruckJpaEntity {
    @Id
    private String licensePlate;

    @Column(nullable = false)
    private BigDecimal payloadWeight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType materialType;

    protected TruckJpaEntity() {
        // Required by JPA
    }

    public TruckJpaEntity(String licensePlate, BigDecimal payloadWeight, MaterialType materialType) {
        this.licensePlate = licensePlate;
        this.payloadWeight = payloadWeight;
        this.materialType = materialType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public BigDecimal getPayloadWeight() {
        return payloadWeight;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
}




