package be.kdg.prgramming6.landside.adapters.in;

public class CheckArrivalTruckResponseDTO {
    private boolean onTime;

    public CheckArrivalTruckResponseDTO() {
    }

    public CheckArrivalTruckResponseDTO(boolean onTime) {
        this.onTime = onTime;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public void setOnTime(boolean onTime) {
        this.onTime = onTime;
    }
}