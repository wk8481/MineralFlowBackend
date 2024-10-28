// landside/src/test/java/be/kdg/prgramming6/landside/CheckTruckArrivalUseCaseImplMockingTest.java
package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.core.CheckTruckArrivalUseCaseImpl;
import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.LoadTrucksByDaySchedulePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckTruckArrivalUseCaseImplMockingTest {
    private CheckTruckArrivalUseCaseImpl sut;
    private LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort;

    @BeforeEach
    void setUp() {
        loadTrucksByDaySchedulePort = mock(LoadTrucksByDaySchedulePort.class);
        sut = new CheckTruckArrivalUseCaseImpl(loadTrucksByDaySchedulePort);
    }

    @Test
    void shouldLoadAllAppointments() {
        // Arrange
        List<Appointment> appointments = List.of(
                new Appointment(new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10"), MaterialType.PET_COKE, LocalDateTime.now(), LocalDateTime.now().plusHours(1), TestIds.SELLER_ID),
                new Appointment(new Truck(new LicensePlate("XYZ-789"), MaterialType.GYPSUM, "WD-20"), MaterialType.GYPSUM, LocalDateTime.now(), LocalDateTime.now().plusHours(1), TestIds.SELLER_ID)
        );
        when(loadTrucksByDaySchedulePort.loadTrucksByDaySchedules(any())).thenReturn(appointments);

        // Act
        List<Appointment> result = sut.loadAllAppointments(List.of(LocalDateTime.now()));

        // Assert
        assertEquals(2, result.size());
        assertEquals("ABC-123", result.get(0).getTruck().getLicensePlate().toString());
        assertEquals("XYZ-789", result.get(1).getTruck().getLicensePlate().toString());

        // Verify
        verify(loadTrucksByDaySchedulePort, times(1)).loadTrucksByDaySchedules(any());
    }

    @Test
    void shouldThrowExceptionWhenNoAppointmentsFound() {
        // Arrange
        when(loadTrucksByDaySchedulePort.loadTrucksByDaySchedules(any())).thenReturn(List.of());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sut.loadAllAppointments(List.of(LocalDateTime.now())));

        // Verify
        verify(loadTrucksByDaySchedulePort, times(1)).loadTrucksByDaySchedules(any());
    }

    @Test
    void shouldSetArrivalTimeIfNull() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment(new Truck(new LicensePlate("ABC-123"), MaterialType.PET_COKE, "WD-10"), MaterialType.PET_COKE, now, now.plusHours(1), TestIds.SELLER_ID);
        appointment.setArrivalTime(null);
        when(loadTrucksByDaySchedulePort.loadTrucksByDaySchedules(any())).thenReturn(List.of(appointment));

        // Act
        sut.loadAllAppointments(List.of(LocalDateTime.now()));

        // Assert
        assertNotNull(appointment.getArrivalTime());

        // Verify
        verify(loadTrucksByDaySchedulePort, times(1)).loadTrucksByDaySchedules(any());
    }
}