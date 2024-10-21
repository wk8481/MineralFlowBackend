package be.kdg.prgramming6.landside.adapter.out.db;

import jakarta.persistence.*;
import be.kdg.prgramming6.landside.domain.MaterialType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "warehouse_projection")
public class WarehouseProjectionJpaEntity {

    @Id
    @Column(name = "warehouse_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID warehouseId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "seller_id", nullable = false, columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sellerId;

    @Column(name = "current_capacity", nullable = false)
    private BigDecimal currentCapacity;


    // Default constructor for JPA
    protected WarehouseProjectionJpaEntity() {
    }

    public WarehouseProjectionJpaEntity(UUID warehouseId, MaterialType materialType, UUID sellerId, LocalDateTime timestamp, BigDecimal currentCapacity) {
        this.warehouseId = warehouseId;
        this.materialType = materialType;
        this.sellerId = sellerId;
        this.timestamp = LocalDateTime.now();
        this.currentCapacity = currentCapacity;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getCurrentCapacity() {
        return currentCapacity;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setCurrentCapacity(BigDecimal currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
