package be.kdg.prgramming6.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, UUID> {
}
