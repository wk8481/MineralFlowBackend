package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.common.exception.InvalidOperationException;
import be.kdg.prgramming6.landside.domain.MaterialType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class MakeAppointmentRequestDTO {

    @NotNull(message = "Seller ID cannot be null.")
    private final UUID sellerId;

    @NotEmpty(message = "License plate cannot be empty.")
    private final String licensePlate;

    @NotEmpty(message = "Material type cannot be empty.")
    private final String materialType;

    @NotNull(message = "Appointment window start cannot be null.")
    private final LocalDateTime appointmentWindowStart;

    @NotNull(message = "Appointment window end cannot be null.")
    private final LocalDateTime appointmentWindowEnd;

    // Constructors, Getters, and Setters
    public MakeAppointmentRequestDTO(UUID sellerId, String licensePlate, String materialType,
                                     LocalDateTime appointmentWindowStart, LocalDateTime appointmentWindowEnd) {
        this.sellerId = sellerId;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.appointmentWindowStart = appointmentWindowStart;
        this.appointmentWindowEnd = appointmentWindowEnd;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public LocalDateTime getAppointmentWindowStart() {
        return appointmentWindowStart;
    }

    public LocalDateTime getAppointmentWindowEnd() {
        return appointmentWindowEnd;
    }

    public void validate() {
        if (!isValidWindowTime()) {
            throw new InvalidOperationException("Start time cannot be after end time.");
        }
        if (MaterialType.fromString(materialType) == null) {
            throw new InvalidOperationException("Invalid material type.");
        }
    }

    private boolean isValidWindowTime() {
        return appointmentWindowStart.isBefore(appointmentWindowEnd);
    }
}
