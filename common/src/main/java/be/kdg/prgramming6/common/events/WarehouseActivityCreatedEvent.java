package be.kdg.prgramming6.common.events;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WarehouseActivityCreatedEvent(UUID warehouseId, WarehouseActivityType type, String materialType, UUID sellerId, LocalDateTime time, BigDecimal weight) {
}
