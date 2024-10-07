package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
    WarehouseJpaEntity findByWarehouseNumber(UUID warehouseId); // Custom method to find warehouse by number
}

