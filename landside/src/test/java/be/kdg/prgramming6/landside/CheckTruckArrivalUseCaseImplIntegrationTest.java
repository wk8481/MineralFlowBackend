package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaRepository;
import be.kdg.prgramming6.landside.adapter.out.db.ScheduleJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.ScheduleJpaRepository;
import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class CheckTruckArrivalUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private CheckArrivalTruckUseCase checkArrivalTruckUseCase;

    @Autowired
    private ScheduleJpaRepository scheduleJpaRepository;

    @Autowired
    private AppointmentJpaRepository appointmentJpaRepository;



    @Test
    void shouldLoadSpecificAppointment() {
        // Arrange: Create and save a schedule entity
        ScheduleJpaEntity scheduleJpaEntity = new ScheduleJpaEntity(TestIds.SCHEDULE_TIME);
        scheduleJpaRepository.save(scheduleJpaEntity);

        // Retrieve the saved schedule by its schedule time
        ScheduleJpaEntity savedSchedule = scheduleJpaRepository.findByScheduleTimeTest(TestIds.SCHEDULE_TIME);

        // Create and save an appointment linked to the retrieved schedule
        AppointmentJpaEntity appointment = new AppointmentJpaEntity(
                savedSchedule,
                TestIds.TRUCK_ID,
                "CEMENT",
                TestIds.WINDOW_START,
                TestIds.WINDOW_END,
                TestIds.SELLER_ID.id(),
                TestIds.ARRIVAL_TIME,
                TestIds.DEPARTURE_TIME
        );
        appointmentJpaRepository.save(appointment);

        // Retrieve the saved appointment by its license plate
        AppointmentJpaEntity retrievedAppointment = appointmentJpaRepository.findByLicensePlateTest(TestIds.TRUCK_ID);

        // Act: Call the use case method to load all appointments for the specified schedule time
        List<Appointment> result = checkArrivalTruckUseCase.loadAllAppointments(List.of(TestIds.SCHEDULE_TIME));

        // Assert: Verify only one appointment was loaded with matching details
        assertEquals(1, result.size(), "Expected exactly one appointment to be loaded");
        Appointment loadedAppointment = result.get(0);
        assertEquals(retrievedAppointment.getLicensePlate(), loadedAppointment.getTruck().getLicensePlate().toString(), "Expected truck ID to match");
        assertEquals("CEMENT", loadedAppointment.getMaterialType().toString(), "Expected material type to match");
        assertEquals(TestIds.WINDOW_START, loadedAppointment.getWindowStart(), "Expected window start time to match");
        assertEquals(TestIds.WINDOW_END, loadedAppointment.getWindowEnd(), "Expected window end time to match");
        assertEquals(TestIds.ARRIVAL_TIME, loadedAppointment.getArrivalTime(), "Expected arrival time to match");
        assertEquals(TestIds.DEPARTURE_TIME, loadedAppointment.getDepartureTime(), "Expected departure time to match");
    }
}
