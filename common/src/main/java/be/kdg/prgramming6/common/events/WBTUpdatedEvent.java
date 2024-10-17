package be.kdg.prgramming6.common.events;

import java.math.BigDecimal;
import java.util.UUID;

public record WBTUpdatedEvent(String licensePlate, BigDecimal netWeight, UUID warehouseId) {
}
