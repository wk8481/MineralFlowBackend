package be.kdg.prgramming6.warehouse.adapter.out.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PayloadDeliveryTicketJpaRepository extends JpaRepository<PayloadDeliveryTicketJpaEntity, UUID> {

    Optional<PayloadDeliveryTicketJpaEntity> findByLicensePlate(String licensePlate);
}
