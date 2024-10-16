package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.WarehouseId;

import java.math.BigDecimal;


public record UpdateWarehouseCapacityCommand(WarehouseId warehouseId, BigDecimal weight) {
    public UpdateWarehouseCapacityCommand {
        if (weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
    }
}