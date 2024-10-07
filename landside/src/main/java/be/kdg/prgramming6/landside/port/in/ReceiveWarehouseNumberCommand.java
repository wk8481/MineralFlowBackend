package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.WeighbridgeNumber;

import java.math.BigDecimal;
import java.util.Objects;

public record ReceiveWarehouseNumberCommand(LicensePlate licensePlate, WeighbridgeNumber weighbridgeNumber, BigDecimal payloadWeight
) {
    public ReceiveWarehouseNumberCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(weighbridgeNumber, "Weigh bridge ID cannot be null");
        Objects.requireNonNull(payloadWeight, "Payload weight cannot be null");

    }

}
