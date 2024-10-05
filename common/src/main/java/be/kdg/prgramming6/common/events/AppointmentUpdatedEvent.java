package be.kdg.prgramming6.common.events;


import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdatedEvent (UUID sellerId, String licensePlate) {
}
