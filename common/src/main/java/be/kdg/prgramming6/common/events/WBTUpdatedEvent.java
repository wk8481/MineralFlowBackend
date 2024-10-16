package be.kdg.prgramming6.common.events;

import java.math.BigDecimal;
import java.util.UUID;

public record WBTUpdatedEvent(UUID sellerId, BigDecimal netWeight, String materialType, String licensePlate) {
}
