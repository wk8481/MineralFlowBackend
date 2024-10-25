package be.kdg.prgramming6.landside.adapter.out.db;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "weighbridge_ticket", catalog = "landside")
public class WeighbridgeTicketJpaEntity {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ticketId = UUID.randomUUID();

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "gross_weight")
    private BigDecimal grossWeight;

    @Column(name = "tare_weight")
    private BigDecimal tareWeight;

    @Column(name = "net_weight")
    private BigDecimal netWeight;

    // Assuming timestamp is a field in WeighbridgeTicketJpaEntity
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    // Getters and setters
    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }
}
