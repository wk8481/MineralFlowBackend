package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;
import be.kdg.prgramming6.warehouse.domain.MaterialType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "warehouse_activities")
public class WarehouseActivityJpaEntity {
    @EmbeddedId
    public WarehouseActivityJpaId id;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private WarehouseActivityType type;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "seller_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sellerId;


    @Column(name = "material_type")
    @Enumerated(value = EnumType.STRING)
    private MaterialType materialType;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", insertable = false, updatable = false)
    private WarehouseJpaEntity warehouse;

    public WarehouseActivityJpaEntity() {
    }

    public WarehouseActivityJpaEntity(final WarehouseActivityJpaId id) {
        this.id = id;
    }

    public WarehouseActivityJpaId getId() {
        return id;
    }

    public void setId(final WarehouseActivityJpaId id) {
        this.id = id;
    }

    public WarehouseActivityType getType() {
        return type;
    }

    public void setType(final WarehouseActivityType type) {
        this.type = type;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public WarehouseJpaEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseJpaEntity warehouse) {
        this.warehouse = warehouse;
    }
}
