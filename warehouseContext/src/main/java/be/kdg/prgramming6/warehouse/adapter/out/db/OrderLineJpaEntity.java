package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(catalog = "warehouse", name = "order_line")
public class OrderLineJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type")
    private MaterialType materialType;

    @Column(name = "amount_in_tons")
    private int amountInTons;

    @Column(name = "price_per_ton")
    private BigDecimal pricePerTon;

    @ManyToOne
    @JoinColumn(name = "po_number", referencedColumnName = "po_number", insertable = false, updatable = false)
    private PurchaseOrderJpaEntity purchaseOrder;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public int getAmountInTons() {
        return amountInTons;
    }

    public void setAmountInTons(int amountInTons) {
        this.amountInTons = amountInTons;
    }

    public BigDecimal getPricePerTon() {
        return pricePerTon;
    }

    public void setPricePerTon(BigDecimal pricePerTon) {
        this.pricePerTon = pricePerTon;
    }

    public PurchaseOrderJpaEntity getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderJpaEntity purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}