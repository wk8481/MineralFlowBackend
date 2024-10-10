package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseProjectionJpaRepository extends JpaRepository<WarehouseProjectionJpaEntity, UUID> {
    // New method to find warehouse by sellerId, materialType, and timestamp
    Optional<WarehouseProjectionJpaEntity> findFirstBySellerIdAndMaterialTypeAndTimestampLessThanEqualOrderByTimestampDesc(
            UUID sellerId, MaterialType materialType, LocalDateTime timestamp
            // Adjust according to your MaterialType class
            // Use LocalDateTime instead of long
    );
}
