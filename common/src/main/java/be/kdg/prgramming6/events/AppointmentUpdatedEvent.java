package be.kdg.prgramming6.events;


import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdatedEvent (UUID sellerId, String licensePLate) {
}
