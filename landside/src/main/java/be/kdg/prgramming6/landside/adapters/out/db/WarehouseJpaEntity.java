package be.kdg.prgramming6.landside.adapters.out.db;

import jakarta.persistence.*;
import be.kdg.prgramming6.landside.domain.MaterialType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.SQLType;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "warehouse")
public class WarehouseJpaEntity {

    @Id
    @Column(name = "warehouse_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID warehouseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "seller_id", nullable = false, columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sellerId;


    // Default constructor for JPA
    protected WarehouseJpaEntity() {
    }

    public WarehouseJpaEntity(UUID warehouseId, MaterialType materialType, UUID sellerId) {
        this.warehouseId = warehouseId;
        this.materialType = materialType;
        this.sellerId = sellerId;
    }



    // Getters
    public UUID getWarehouseId() {
        return warehouseId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }


    public UUID getSellerId() {
        return sellerId;
    }
}
