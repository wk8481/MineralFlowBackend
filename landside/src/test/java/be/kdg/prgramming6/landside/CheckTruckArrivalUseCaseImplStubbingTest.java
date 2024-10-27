// landside/src/test/java/be/kdg/prgramming6/landside/CheckTruckArrivalUseCaseImplStubbingTest.java
package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.core.CheckTruckArrivalUseCaseImpl;
import be.kdg.prgramming6.landside.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckTruckArrivalUseCaseImplStubbingTest {
    private CheckTruckArrivalUseCaseImpl sut;

    @BeforeEach
    void setUp() {
        // Initialize with an empty list or any default list if needed
        LoadAppointmentPortStub loadAppointmentPortStub = new LoadAppointmentPortStub(List.of());
        sut = new CheckTruckArrivalUseCaseImpl(loadAppointmentPortStub);
    }

    @Test
    void shouldLoadAllAppointments() {
        // Arrange
        List<Appointment> appointments = List.of(
                new Appointment(new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10"), MaterialType.PET_COKE, LocalDateTime.now(), LocalDateTime.now().plusHours(1), TestIds.SELLER_ID),
                new Appointment(new Truck(new LicensePlate("XYZ-789"), MaterialType.GYPSUM, "WD-20"), MaterialType.GYPSUM, LocalDateTime.now(), LocalDateTime.now().plusHours(1), TestIds.SELLER_ID)
        );
        LoadAppointmentPortStub loadAppointmentPortStub = new LoadAppointmentPortStub(appointments);
        sut = new CheckTruckArrivalUseCaseImpl(loadAppointmentPortStub);

        // Act
        List<Appointment> result = sut.loadAllAppointments();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ABC-123", result.get(0).getTruck().getLicensePlate().toString());
        assertEquals("XYZ-789", result.get(1).getTruck().getLicensePlate().toString());
    }

    @Test
    void shouldThrowExceptionWhenNoAppointmentsFound() {
        // Arrange
        LoadAppointmentPortStub loadAppointmentPortStub = new LoadAppointmentPortStub(List.of());
        sut = new CheckTruckArrivalUseCaseImpl(loadAppointmentPortStub);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sut.loadAllAppointments());
    }

    @Test
    void shouldSetArrivalTimeIfNull() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment(new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10"), MaterialType.PET_COKE, now, now.plusHours(1), TestIds.SELLER_ID);
        appointment.setArrivalTime(null);
        LoadAppointmentPortStub loadAppointmentPortStub = new LoadAppointmentPortStub(List.of(appointment));
        sut = new CheckTruckArrivalUseCaseImpl(loadAppointmentPortStub);

        // Act
        sut.loadAllAppointments();

        // Assert
        assertNotNull(appointment.getArrivalTime());
    }
}