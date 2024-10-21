package be.kdg.prgramming6.landside.port.in;

public class TruckResponse {
    private final String licensePlate;

    public TruckResponse(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}