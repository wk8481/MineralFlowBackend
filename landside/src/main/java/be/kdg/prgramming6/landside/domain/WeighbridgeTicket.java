package be.kdg.prgramming6.landside.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WeighbridgeTicket {
    private final String licensePlate;
    private  BigDecimal grossWeight;
    private final BigDecimal tareWeight;
    private  BigDecimal netWeight;
    private LocalDateTime timestamp;

    public WeighbridgeTicket(String licensePlate, BigDecimal grossWeight, BigDecimal tareWeight, BigDecimal netWeight, LocalDateTime timestamp) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = netWeight != null ? netWeight : calculateNetWeight(grossWeight, tareWeight);
        this.timestamp = timestamp;
    }

    public WeighbridgeTicket(String licensePlate, BigDecimal grossWeight, BigDecimal tareWeight, LocalDateTime timestamp) {
        this.licensePlate = licensePlate;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = calculateNetWeight(grossWeight, tareWeight);
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

    private static BigDecimal calculateNetWeight(BigDecimal grossWeight, BigDecimal tareWeight) {
        return grossWeight.subtract(tareWeight);
    }

    private BigDecimal calculateGrossWeight() {
        return netWeight.add(tareWeight);
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}