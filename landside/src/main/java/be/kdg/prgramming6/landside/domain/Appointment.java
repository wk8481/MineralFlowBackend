package be.kdg.prgramming6.landside.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private final Truck truck;
    private final MaterialType materialType;
    private final LocalDateTime windowStart;
    private final LocalDateTime windowEnd;
    private final SellerId sellerId;

    public Appointment(final Truck truck, final MaterialType materialType,
                       final LocalDateTime windowStart, final LocalDateTime windowEnd, final SellerId sellerId) {

        if (truck == null || materialType == null || windowStart == null ||
                windowEnd == null || sellerId == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (windowStart.isAfter(windowEnd)) {
            throw new IllegalArgumentException("Window start cannot be after window end");
        }
        if (windowStart.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }

        this.truck = truck;
        this.materialType = materialType;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.sellerId = sellerId;
    }

    // Method to check if this appointment overlaps with another time window
    public boolean overlapsWith(LocalDateTime start, LocalDateTime end) {
        return (start.isBefore(windowEnd) && end.isAfter(windowStart));
    }

    // Getters
    public Truck getTruck() {
        return truck;
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

    public SellerId getSellerId() {
        return sellerId;
    }

    // Method to create a new Appointment instance
    public static Appointment scheduleAppointment(Truck truck, MaterialType materialType,
                                                  LocalDateTime windowStart, LocalDateTime windowEnd,
                                                  SellerId sellerId) {
        return new Appointment(truck, materialType, windowStart, windowEnd, sellerId);
    }
}
