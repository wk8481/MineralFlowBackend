package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "warehouses")
public class WarehouseJpaEntity {

    @Id
    @Column(name = "warehouse_id")
    private UUID warehouseId; // UUID for warehouse identification

    @Column(name = "seller_id")
    private UUID sellerId; // Reference to the sellerId

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type")
    private MaterialType materialType;


    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<WarehouseActivityJpaEntity> activities;



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

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public List<WarehouseActivityJpaEntity> getActivities() {
        return activities;
    }

    public void setActivities(List<WarehouseActivityJpaEntity> activities) {
        this.activities = activities;
    }








}
