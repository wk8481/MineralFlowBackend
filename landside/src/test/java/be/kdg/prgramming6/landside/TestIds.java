package be.kdg.prgramming6.landside;

import be.kdg.prgramming6.landside.domain.SellerId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestIds {
    public static final SellerId SELLER_ID = new SellerId(UUID.fromString("96712d0d-dd10-4802-9d62-1d30fa638422"));
    public static final LocalDateTime SCHEDULE_TIME = LocalDateTime.of(2024, 10, 30, 10, 0, 0);
    public static final LocalDateTime WINDOW_START = SCHEDULE_TIME;
    public static final LocalDateTime WINDOW_END = LocalDateTime.of(2024, 10, 30, 10, 59, 0);
    public static final LocalDateTime ARRIVAL_TIME = LocalDateTime.of(2024, 10, 30, 10, 15, 0);
    public static final LocalDateTime DEPARTURE_TIME = LocalDateTime.of(2024, 10, 30, 10, 45, 0);
    public static final String TRUCK_ID = "TRUCK_ID";

    private TestIds() {
    }
}
