package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface WeighbridgeTicketJpaRepository extends JpaRepository<WeighbridgeTicketJpaEntity, UUID> {
    Optional<WeighbridgeTicketJpaEntity> findByLicensePlate(String licensePlate);
}