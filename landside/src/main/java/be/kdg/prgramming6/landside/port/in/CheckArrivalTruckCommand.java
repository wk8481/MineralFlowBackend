package be.kdg.prgramming6.landside.port.in;

import java.time.LocalDateTime;

public record CheckArrivalTruckCommand(String licensePlate, LocalDateTime arrivalTime) {

    public CheckArrivalTruckCommand {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Arrival time cannot be null");
        }
    }
}
