package be.kdg.prgramming6.landside.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeighbridgeJpaRepository extends JpaRepository<WeighbridgeJpaEntity, String> {
    WeighbridgeJpaEntity findByWeighbridgeNumber(String weighbridgeId); // Custom method to find weighbridge by number
}
