package be.kdg.prgramming6.warehouse.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrderJpaEntity, String> {
    Optional<PurchaseOrderJpaEntity> findByPoNumber(String poNumber);
}