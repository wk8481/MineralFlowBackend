package be.kdg.prgramming6.landside.port.in;

import java.math.BigDecimal;
import java.util.Objects;

public record GenerateWeighbridgeTicketCommand(
        String licensePlate,
        BigDecimal grossWeight,
        BigDecimal tareWeight,
        BigDecimal netWeight
) {
    public GenerateWeighbridgeTicketCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(grossWeight, "Gross weight cannot be null");
        Objects.requireNonNull(tareWeight, "Tare weight cannot be null");
        Objects.requireNonNull(netWeight, "Net weight cannot be null");


        if (grossWeight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Gross weight must be greater than zero");
        }

        if (tareWeight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tare weight must be greater than zero");
        }

        if (netWeight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Net weight must be greater than zero");
        }



    }


}