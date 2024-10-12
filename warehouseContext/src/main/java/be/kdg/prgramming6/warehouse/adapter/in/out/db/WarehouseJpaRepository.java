package be.kdg.prgramming6.warehouse.adapter.in.out.db;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
    Optional<WarehouseJpaEntity> findBySellerIdAndMaterialType(UUID sellerId, MaterialType materialType);

    Optional<WarehouseJpaEntity> findByWarehouseId(UUID warehouseId);

    @Query("FROM WarehouseJpaEntity w JOIN FETCH w.activities WHERE w.warehouseId = :warehouseId")
    WarehouseJpaEntity findByWarehouseIdIncludingActivities(UUID warehouseId);


}
