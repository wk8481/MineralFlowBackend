package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.WeighBridgeID;

import java.math.BigDecimal;
import java.util.Objects;

public record ReceiveWarehouseNumberCommand(LicensePlate licensePlate, WeighBridgeID weighBridgeID, BigDecimal payloadWeight
) {
    public ReceiveWarehouseNumberCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(weighBridgeID, "Weigh bridge ID cannot be null");
        Objects.requireNonNull(payloadWeight, "Payload weight cannot be null");

    }

}
