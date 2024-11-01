package be.kdg.prgramming6.landside.adapter.in;

import java.time.LocalDateTime;

public class TruckOnTimeDTO {
    private String licensePlate;
    private String sellerId;
    private String materialType;
    private LocalDateTime arrivalTime;
    private boolean onTime;
    private LocalDateTime windowStart;
    private LocalDateTime windowEnd;

    public TruckOnTimeDTO() {
    }

    public TruckOnTimeDTO(String licensePlate, String sellerId, String materialType, LocalDateTime arrivalTime, boolean onTime, LocalDateTime windowStart, LocalDateTime windowEnd) {
        this.licensePlate = licensePlate;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.arrivalTime = arrivalTime;
        this.onTime = onTime;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(LocalDateTime windowEnd) {
        this.windowEnd = windowEnd;
    }

    public LocalDateTime getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(LocalDateTime windowStart) {
        this.windowStart = windowStart;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public void setOnTime(boolean onTime) {
        this.onTime = onTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}