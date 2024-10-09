package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, UUID> {
    ScheduleJpaEntity findByDay(LocalDate day); // Custom method to find schedule by day
}

