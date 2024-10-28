package be.kdg.prgramming6.landside.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, LocalDateTime> {
    ScheduleJpaEntity findByScheduleTime(LocalDateTime scheduleTime); // Custom method to find schedule by day

    @Query("SELECT s FROM ScheduleJpaEntity s LEFT JOIN FETCH s.appointments WHERE s.scheduleTime = :scheduleTime")
    ScheduleJpaEntity findByScheduleTimeWithAppointments(@Param("scheduleTime") LocalDateTime scheduleTime);
}

