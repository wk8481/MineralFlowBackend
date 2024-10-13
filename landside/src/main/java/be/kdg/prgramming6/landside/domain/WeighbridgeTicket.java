package be.kdg.prgramming6.landside.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WeighbridgeTicket {
    private final String licensePlate;
    private final BigDecimal grossWeight;
    private final BigDecimal tareWeight;
    private final BigDecimal netWeight;
    private final LocalDateTime timestamp;

    public WeighbridgeTicket(String licensePlate, BigDecimal grossWeight, BigDecimal tareWeight, LocalDateTime timestamp) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = calculateNetWeight();
        this.timestamp = timestamp;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    private BigDecimal calculateNetWeight() {
        return grossWeight.subtract(tareWeight);
    }


}