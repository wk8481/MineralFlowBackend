package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaRepository;
import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.MaterialType;
import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CheckTruckArrivalUseCaseImplIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private CheckArrivalTruckUseCase checkArrivalTruckUseCase;
    @Autowired
    private AppointmentJpaRepository appointmentJpaRepository;

    @BeforeEach
    void setUp() {
        appointmentJpaRepository.deleteAll();
    }

    @Test
    void shouldLoadAllAppointments() {
        // Arrange
        Appointment appointment1 = new Appointment(
                new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10", TestIds.SELLER_ID),
                MaterialType.PET_COKE,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                TestIds.SELLER_ID
        );
        Appointment appointment2 = new Appointment(
                new Truck(new LicensePlate("XYZ-789"), MaterialType.GYPSUM, "WD-20", TestIds.SELLER_ID),
                MaterialType.GYPSUM,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                TestIds.SELLER_ID
        );
        appointmentJpaRepository.saveAll(List.of(toJpaEntity(appointment1), toJpaEntity(appointment2)));

        // Act
        List<Appointment> result = checkArrivalTruckUseCase.loadAllAppointments();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ABC-123", result.get(0).getTruck().getLicensePlate().toString());
        assertEquals("XYZ-789", result.get(1).getTruck().getLicensePlate().toString());
    }

    @Test
    void shouldThrowExceptionWhenNoAppointmentsFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> checkArrivalTruckUseCase.loadAllAppointments());
    }

    @Test
    void shouldSetArrivalTimeIfNull() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment(
                new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10", TestIds.SELLER_ID),
                MaterialType.PET_COKE,
                now,
                now.plusHours(1),
                TestIds.SELLER_ID
        );
        appointment.setArrivalTime(null);
        appointmentJpaRepository.save(toJpaEntity(appointment));

        // Act
        List<Appointment> result = checkArrivalTruckUseCase.loadAllAppointments();

        // Assert
        assertNotNull(result.get(0).getArrivalTime());
    }

    private AppointmentJpaEntity toJpaEntity(Appointment appointment) {
        AppointmentJpaEntity entity = new AppointmentJpaEntity();
        entity.setLicensePlate(appointment.getTruck().getLicensePlate().toString());
        entity.setMaterialType(appointment.getMaterialType().name());
        entity.setWindowStart(appointment.getWindowStart());
        entity.setWindowEnd(appointment.getWindowEnd());
        entity.setSellerId(appointment.getSellerId().id());
        entity.setArrivalTime(appointment.getArrivalTime());
        entity.setDepartureTime(appointment.getDepartureTime());
        return entity;
    }
}