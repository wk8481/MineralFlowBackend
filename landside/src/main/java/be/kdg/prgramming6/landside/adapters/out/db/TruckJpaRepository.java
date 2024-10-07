package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckJpaRepository extends JpaRepository<TruckJpaEntity, String> {
    TruckJpaEntity findByTruckNumber(String licensePlate); // Custom method to find truck by number

}
