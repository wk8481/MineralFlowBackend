package be.kdg.prgramming6.warehouse.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WarehouseActivityJpaRepository extends JpaRepository<WarehouseActivityJpaEntity, WarehouseActivityJpaId> {
    List<WarehouseActivityJpaEntity> findAllById_WarehouseIdAndTimeAfter(UUID warehouseId, LocalDateTime time);
}
