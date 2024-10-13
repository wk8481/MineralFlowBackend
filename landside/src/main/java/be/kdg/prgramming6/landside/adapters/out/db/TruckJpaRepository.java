package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckJpaRepository extends JpaRepository<TruckJpaEntity, String> {
    Optional<TruckJpaEntity> findByLicensePlate(String licensePlate);
}
