package be.kdg.prgramming6.adapter.in.out.db;

import be.kdg.prgramming6.domain.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
    WarehouseJpaEntity findBySellerId(UUID sellerId); // Adjust the parameter type to UUID for consistency

    Optional<WarehouseJpaEntity> findBySellerIdAndMaterialType(UUID sellerId, MaterialType materialType);

}
