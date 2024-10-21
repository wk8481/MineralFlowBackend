package be.kdg.prgramming6.landside.adapters.in;

import java.time.LocalDateTime;

public class CheckArrivalTruckRequestDTO {
    private String licensePlate;
    private LocalDateTime arrivalTime;

    public CheckArrivalTruckRequestDTO() {
    }

    public CheckArrivalTruckRequestDTO(String licensePlate, LocalDateTime arrivalTime) {
        this.licensePlate = licensePlate;
        this.arrivalTime = arrivalTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}