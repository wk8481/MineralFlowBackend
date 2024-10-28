package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.ScheduleJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.ScheduleJpaRepository;
import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
class CheckTruckArrivalUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private CheckArrivalTruckUseCase checkArrivalTruckUseCase;

    @Autowired
    private ScheduleJpaRepository scheduleJpaRepository;

    @BeforeEach
    void setUp() {
        createTestData();
    }

    private void createTestData() {
        LocalDateTime now = LocalDateTime.now();
        ScheduleJpaEntity schedule = new ScheduleJpaEntity();
        schedule.setScheduleTime(now);

        AppointmentJpaEntity appointment1 = new AppointmentJpaEntity();
        appointment1.setLicensePlate("ABC-123");
        appointment1.setMaterialType("PET_COKE");
        appointment1.setWindowStart(now);
        appointment1.setWindowEnd(now.plusHours(1));
        appointment1.setSellerId(UUID.fromString("96712d0d-dd10-4802-9d62-1d30fa638422"));
        appointment1.setSchedule(schedule);

        AppointmentJpaEntity appointment2 = new AppointmentJpaEntity();
        appointment2.setLicensePlate("XYZ-789");
        appointment2.setMaterialType("GYPSUM");
        appointment2.setWindowStart(now);
        appointment2.setWindowEnd(now.plusHours(1));
        appointment2.setSellerId(UUID.fromString("96712d0d-dd10-4802-9d62-1d30fa638422"));
        appointment2.setSchedule(schedule);

        schedule.setAppointments(List.of(appointment1, appointment2));
        scheduleJpaRepository.save(schedule);
    }

    @Test
    void shouldLoadAllAppointments() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        List<Appointment> result = checkArrivalTruckUseCase.loadAllAppointments(List.of(now));

        // Assert
        assertFalse(result.isEmpty(), "Expected appointments to be loaded");
    }
}