package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.MaterialType;
import be.kdg.prgramming6.landside.domain.SellerId;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "trucks", catalog = "landside")
public class TruckJpaEntity {
    @Id
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "dock_number")
    private String dockNumber;

    @Column(name = "seller_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sellerId;

    // Getters and setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getDockNumber() {
        return dockNumber;
    }

    public void setDockNumber(String dockNumber) {
        this.dockNumber = dockNumber;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }
}