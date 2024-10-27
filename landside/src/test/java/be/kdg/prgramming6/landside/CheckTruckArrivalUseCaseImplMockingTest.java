package be.kdg.prgramming6.landside;
import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.core.CheckTruckArrivalUseCaseImpl;
import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.LoadAppointmentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckTruckArrivalUseCaseImplMockingTest {
    private CheckTruckArrivalUseCaseImpl sut;
    private LoadAppointmentPort loadAppointmentPort;

    @BeforeEach
    void setUp() {
        loadAppointmentPort = mock(LoadAppointmentPort.class);
        sut = new CheckTruckArrivalUseCaseImpl(loadAppointmentPort);
    }

    @Test
    void shouldLoadAllAppointments() {
        // Arrange
        List<Appointment> appointments = List.of(
                new Appointment(new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10"), MaterialType.PET_COKE, LocalDateTime.now(), LocalDateTime.now().plusHours(1), TestIds.SELLER_ID),
                new Appointment(new Truck(new LicensePlate("XYZ-789"), MaterialType.GYPSUM, "WD-20"), MaterialType.GYPSUM, LocalDateTime.now(), LocalDateTime.now().plusHours(1), TestIds.SELLER_ID)
        );
        when(loadAppointmentPort.loadAllAppointments()).thenReturn(appointments);

        // Act
        List<Appointment> result = sut.loadAllAppointments();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ABC-123", result.get(0).getTruck().getLicensePlate().toString());
        assertEquals("XYZ-789", result.get(1).getTruck().getLicensePlate().toString());

        // Verify
        verify(loadAppointmentPort, times(1)).loadAllAppointments();
    }

    @Test
    void shouldThrowExceptionWhenNoAppointmentsFound() {
        // Arrange
        when(loadAppointmentPort.loadAllAppointments()).thenReturn(List.of());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sut.loadAllAppointments());

        // Verify
        verify(loadAppointmentPort, times(1)).loadAllAppointments();
    }

    @Test
    void shouldSetArrivalTimeIfNull() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment(new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10"), MaterialType.PET_COKE, now, now.plusHours(1), TestIds.SELLER_ID);
        appointment.setArrivalTime(null);
        when(loadAppointmentPort.loadAllAppointments()).thenReturn(List.of(appointment));

        // Act
        sut.loadAllAppointments();

        // Assert
        assertNotNull(appointment.getArrivalTime());

        // Verify
        verify(loadAppointmentPort, times(1)).loadAllAppointments();
    }
}