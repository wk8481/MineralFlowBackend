package be.kdg.prgramming6.landside.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, LocalDateTime> {
    ScheduleJpaEntity findByScheduleTime(LocalDateTime scheduleTime); // Custom method to find schedule by day

   @Query("FROM ScheduleJpaEntity s WHERE s.scheduleTime = :scheduleTime")
    ScheduleJpaEntity findByScheduleTimeTest(@Param("scheduleTime") LocalDateTime scheduleTime); // Custom method to find schedule by day including appointments


}

