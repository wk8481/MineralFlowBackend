package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.AppointmentJpaRepository;
import be.kdg.prgramming6.landside.adapter.out.db.ScheduleJpaEntity;
import be.kdg.prgramming6.landside.adapter.out.db.ScheduleJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
public class CheckArrivalTruckControllerIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScheduleJpaRepository scheduleJpaRepository;

    @Autowired
    private AppointmentJpaRepository appointmentJpaRepository;



    @Test
    @WithMockUser(username = "admin@gmail.com")
    void shouldCheckArrivalTruck() throws Exception {
        // Arrange: Create and save a schedule entity
        ScheduleJpaEntity scheduleJpaEntity = new ScheduleJpaEntity(TestIds.SCHEDULE_TIME);
        scheduleJpaRepository.save(scheduleJpaEntity);

        // Create and save an appointment linked to the retrieved schedule
        AppointmentJpaEntity appointment = new AppointmentJpaEntity(
                scheduleJpaEntity,
                TestIds.TRUCK_ID,
                "CEMENT",
                TestIds.WINDOW_START,
                TestIds.WINDOW_END,
                TestIds.SELLER_ID.id(),
                TestIds.ARRIVAL_TIME,
                TestIds.DEPARTURE_TIME
        );
        appointmentJpaRepository.save(appointment);

        // Act: Perform the GET request to check arrival trucks
        ResultActions result = mockMvc.perform(get("/check-arrival").with(csrf()));

        // Format the LocalDateTime to include seconds
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Assert: Verify the response
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].licensePlate", is(TestIds.TRUCK_ID)))
                .andExpect(jsonPath("$[0].materialType", is("CEMENT")))
                .andExpect(jsonPath("$[0].arrivalTime", is(TestIds.ARRIVAL_TIME.format(formatter))))
                .andExpect(jsonPath("$[0].windowStart", is(TestIds.WINDOW_START.format(formatter))))
                .andExpect(jsonPath("$[0].windowEnd", is(TestIds.WINDOW_END.format(formatter))));
    }
}