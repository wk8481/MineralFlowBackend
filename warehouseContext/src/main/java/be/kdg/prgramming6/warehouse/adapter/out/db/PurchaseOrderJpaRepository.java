package be.kdg.prgramming6.warehouse.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrderJpaEntity, String> {

}