package be.kdg.prgramming6.adapters.in;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record MakeAppointmentDTO(
        UUID appointmentId,
        UUID sellerId,
        String licensePlate,
        String materialType,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentWindowStart,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentWindowEnd) {

    public static MakeAppointmentDTO of(final UUID appointmentId, final UUID sellerId, final String licensePlate, final String materialType,
                                        final LocalDateTime appointmentWindowStart, final LocalDateTime appointmentWindowEnd) {
        return new MakeAppointmentDTO(appointmentId, sellerId, licensePlate, materialType, appointmentWindowStart, appointmentWindowEnd);
    }
}
