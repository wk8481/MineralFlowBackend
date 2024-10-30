package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;
import be.kdg.prgramming6.warehouse.domain.FulfillmentStatus;
import be.kdg.prgramming6.warehouse.domain.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseActivityJpaRepository extends JpaRepository<WarehouseActivityJpaEntity, WarehouseActivityJpaId> {
    List<WarehouseActivityJpaEntity> findAllById_WarehouseIdAndTimeAfter(UUID warehouseId, LocalDateTime time);
    List<WarehouseActivityJpaEntity> findAllByMaterialTypeAndFulfillmentStatus_StatusAndType(MaterialType materialType, FulfillmentStatus status, WarehouseActivityType type);



}
