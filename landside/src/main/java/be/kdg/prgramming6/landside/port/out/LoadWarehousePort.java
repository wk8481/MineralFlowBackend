package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.MaterialType;
import be.kdg.prgramming6.landside.domain.SellerId;
import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.domain.WarehouseId;

import java.time.LocalDateTime;
import java.util.Optional;

@FunctionalInterface
public interface LoadWarehousePort {
    Optional<Warehouse> loadWarehouse(SellerId sellerId, MaterialType materialType, LocalDateTime timestamp);
    // Additional load methods based on different attributes can be added here
}
