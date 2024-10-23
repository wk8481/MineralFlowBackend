package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "warehouse", name = "payload_delivery_ticket")
public class PayloadDeliveryTicketJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "VARCHAR(36)", updatable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Column(name = "dock_number", nullable = false)
    private String dockNumber;

    @Column(name= "delivery_date",nullable = false)
    private LocalDateTime deliveryDate;

    // Constructors
    public PayloadDeliveryTicketJpaEntity(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public PayloadDeliveryTicketJpaEntity() {

    }

    public PayloadDeliveryTicketJpaEntity(UUID warehouseId, String licensePlate, MaterialType materialType, String conveyorBeltId, LocalDateTime deliveryDate) {
        this.warehouseId = warehouseId;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.dockNumber = conveyorBeltId;
        this.deliveryDate = deliveryDate;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
