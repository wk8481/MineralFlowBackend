package be.kdg.prgramming6.landside.adapter.in;

import java.time.LocalDateTime;

public class TruckOnTimeDTO {
    private String licensePlate;
    private String sellerId;
    private String materialType;
    private LocalDateTime arrivalTime;
    private boolean onTime;

    public TruckOnTimeDTO() {
    }

    public TruckOnTimeDTO(String licensePlate, String sellerId, String materialType, LocalDateTime arrivalTime, boolean onTime) {
        this.licensePlate = licensePlate;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.arrivalTime = arrivalTime;
        this.onTime = onTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public void setOnTime(boolean onTime) {
        this.onTime = onTime;
    }
}