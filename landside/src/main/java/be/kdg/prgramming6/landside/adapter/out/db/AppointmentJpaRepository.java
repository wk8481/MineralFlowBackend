package be.kdg.prgramming6.landside.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, Integer> {
    Optional<AppointmentJpaEntity> findByLicensePlate(String licensePlate);
}