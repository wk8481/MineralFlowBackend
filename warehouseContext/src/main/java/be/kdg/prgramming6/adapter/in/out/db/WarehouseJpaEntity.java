package be.kdg.prgramming6.adapter.in.out.db;

import be.kdg.prgramming6.domain.MaterialType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "warehouses")
public class WarehouseJpaEntity {

    @Id
    @Column(name = "warehouse_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID warehouseId; // UUID for warehouse identification

    @Column(name = "seller_id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sellerId; // Reference to the SellerId

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;




    // Constructors, getters, setters
    public WarehouseJpaEntity() {
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
}
