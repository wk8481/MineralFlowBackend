package be.kdg.prgramming6.landside.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class GenerateWeighbridgeTicketDTO {
    @NotBlank(message = "License plate cannot be blank")
    private String licensePlate;

    @NotNull(message = "Gross weight cannot be null")
    @Positive(message = "Gross weight must be positive")
    private BigDecimal grossWeight;

    @NotNull(message = "Tare weight cannot be null")
    @Positive(message = "Tare weight must be positive")
    private BigDecimal tareWeight;

    @NotNull(message = "Net weight cannot be null")
    @Positive(message = "Net weight must be positive")
    private BigDecimal netWeight;

    // Getters and Setters
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

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }
}