package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.PurchaseOrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(catalog = "warehouse", name = "purchase_order")
public class PurchaseOrderJpaEntity {
    @Id
    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "date")
    private Date date;

    @Column(name = "customer_number", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID customerNumber;

    @Column(name = "customer_name")
    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseOrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
    private List<OrderLineJpaEntity> orderLines;

    // Getters and setters
    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(UUID customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    public List<OrderLineJpaEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineJpaEntity> orderLines) {
        this.orderLines = orderLines;
    }
}