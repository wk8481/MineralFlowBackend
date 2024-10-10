package be.kdg.prgramming6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, LocalDateTime> {
    ScheduleJpaEntity findByScheduleTime(LocalDateTime scheduleTime); // Custom method to find schedule by day
}

