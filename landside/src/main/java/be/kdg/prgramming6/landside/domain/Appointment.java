// landside/src/main/java/be/kdg/prgramming6/landside/domain/Appointment.java
package be.kdg.prgramming6.landside.domain;

import java.time.LocalDateTime;

public class Appointment {
    private final Truck truck;
    private final MaterialType materialType;
    private final LocalDateTime windowStart;
    private final LocalDateTime windowEnd;
    private final SellerId sellerId;
    private LocalDateTime arrivalTime;

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

    // Method to check the arrival status
    public String checkArrivalStatus() {
        if (arrivalTime == null) {
            return "unknown";
        }
        if (isWithinWindow(arrivalTime)) {
            return "on time";
        } else if (arrivalTime.isBefore(windowStart)) {
            return "early";
        } else {
            return "late";
        }
    }

    // Method to check if a given time is within the appointment window
    public boolean isWithinWindow(LocalDateTime time) {
        return !time.isBefore(windowStart) && !time.isAfter(windowEnd);
    }

    // Getters and setters
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

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Method to create a new Appointment instance
    public static Appointment scheduleAppointment(Truck truck, MaterialType materialType,
                                                  LocalDateTime windowStart, LocalDateTime windowEnd,
                                                  SellerId sellerId) {
        return new Appointment(truck, materialType, windowStart, windowEnd, sellerId);
    }
}