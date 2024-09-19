package be.kdg.prgramming6.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private final UUID appointmentId;
    private final Truck truck;
    private final LicensePlate licensePlate;
    private final MaterialType materialType;
    private final LocalDateTime windowStart;
    private final LocalDateTime windowEnd;


    public Appointment(UUID appointmentId, Truck truck, LicensePlate licensePlate, MaterialType materialType,
                       LocalDateTime windowStart, LocalDateTime windowEnd) {
        if (appointmentId == null || truck == null || licensePlate == null || materialType == null || windowStart == null || windowEnd == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (windowStart.isAfter(windowEnd)) {
            throw new IllegalArgumentException("Window start cannot be after window end");
        }
        if (windowStart.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }

        this.appointmentId = appointmentId;
        this.truck = truck;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;

    }

    // Getter methods
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public Truck getTruck() {
        return truck;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public LocalDateTime getWindowStart() {
        return windowStart;
    }

    public LocalDateTime getWindowEnd() {
        return windowEnd;
    }


    // Method to schedule an appointment
    public static Appointment scheduleAppointment(UUID appointmentId, Truck truck, LicensePlate licensePlate, MaterialType materialType,
                                                 LocalDateTime windowStart, LocalDateTime windowEnd) {
        return new Appointment(appointmentId, truck, licensePlate, materialType, windowStart, windowEnd);
    }
}
