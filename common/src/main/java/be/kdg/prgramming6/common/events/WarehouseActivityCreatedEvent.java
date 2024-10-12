package be.kdg.prgramming6.common.events;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;

import java.math.BigDecimal;
import java.util.UUID;

public record WarehouseActivityCreatedEvent(UUID warehouseId, WarehouseActivityType type, String materialType, BigDecimal weight) {
}
