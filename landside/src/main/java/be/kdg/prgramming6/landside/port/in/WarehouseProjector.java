package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@FunctionalInterface
public interface WarehouseProjector {
    void projectWarehouse(UUID warehouseId, WarehouseActivityType warehouseActivityType, UUID sellerId, String  materialType, LocalDateTime timestamp, BigDecimal weight);
}
