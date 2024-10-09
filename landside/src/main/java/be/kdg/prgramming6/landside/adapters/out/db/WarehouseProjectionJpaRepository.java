package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseProjectionJpaRepository extends JpaRepository<WarehouseProjectionJpaEntity, UUID> {
    WarehouseProjectionJpaEntity findByWarehouseNumber(UUID warehouseId); // Custom method to find warehouse by number

    // New method to find warehouse by sellerId, materialType, and timestamp
    Optional<WarehouseProjectionJpaEntity> findFirstBySellerIdAndMaterialTypeAndTimestampLessThanEqualOrderByTimestampDesc(
            UUID sellerId,
            String materialType, // Adjust according to your MaterialType class
            LocalDateTime timestamp // Use LocalDateTime instead of long
    );
}
