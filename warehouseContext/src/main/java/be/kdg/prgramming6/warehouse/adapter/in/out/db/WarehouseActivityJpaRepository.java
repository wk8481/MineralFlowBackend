package be.kdg.prgramming6.warehouse.adapter.in.out.db;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WarehouseActivityJpaRepository extends JpaRepository<WarehouseActivityJpaEntity, WarehouseActivityJpaId> {
    List<WarehouseActivityJpaEntity> findAllById_WarehouseIdAndTimeAfter(UUID warehouseId, LocalDateTime time);
}
