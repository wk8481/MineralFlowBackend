package be.kdg.prgramming6.landside.port.in;

public class CheckArrivalTruckResponse {
    private final boolean onTime;
    private final String sellerId;
    private final String materialType;

    public CheckArrivalTruckResponse(boolean onTime, String sellerId, String materialType) {
        this.onTime = onTime;
        this.sellerId = sellerId;
        this.materialType = materialType;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getMaterialType() {
        return materialType;
    }
}