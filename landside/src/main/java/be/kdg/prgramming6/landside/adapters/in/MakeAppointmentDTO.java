package be.kdg.prgramming6.landside.adapters.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record MakeAppointmentDTO(UUID sellerId, String licensePlate, String materialType, LocalDateTime appointmentWindowStart, LocalDateTime appointmentWindowEnd) {
    public static MakeAppointmentDTO of(final UUID sellerId, final String licensePlate, final String materialType,
                                        final LocalDateTime appointmentWindowStart, final LocalDateTime appointmentWindowEnd) {
        return new MakeAppointmentDTO(sellerId, licensePlate, materialType, appointmentWindowStart, appointmentWindowEnd);
    }
}


