package be.kdg.programming6.adapter.in.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
    WarehouseJpaEntity findBySellerId(UUID sellerId); // Adjust the parameter type to UUID for consistency
}
