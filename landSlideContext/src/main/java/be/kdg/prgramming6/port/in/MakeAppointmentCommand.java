package be.kdg.prgramming6.port.in;

import be.kdg.prgramming6.domain.LicensePlate;
import be.kdg.prgramming6.domain.MaterialType;
import be.kdg.prgramming6.domain.PersonId;

import java.time.LocalDateTime;
import java.util.Objects;

public record MakeAppointmentCommand(
        LicensePlate licensePlate,
        MaterialType materialType,
        LocalDateTime appointmentWindowStart,
        LocalDateTime appointmentWindowEnd,
        PersonId seller // Added seller field
) {
    public MakeAppointmentCommand {
        Objects.requireNonNull(licensePlate, "License plate cannot be null");
        Objects.requireNonNull(materialType, "Material type cannot be null");
        Objects.requireNonNull(appointmentWindowStart, "Appointment window start cannot be null");
        Objects.requireNonNull(appointmentWindowEnd, "Appointment window end cannot be null");
        Objects.requireNonNull(seller, "Seller cannot be null"); // Validate seller

        if (appointmentWindowStart.isAfter(appointmentWindowEnd)) {
            throw new IllegalArgumentException("Appointment window start cannot be after end");
        }

        if (appointmentWindowStart.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }
    }
}
