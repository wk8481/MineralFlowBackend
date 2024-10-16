package be.kdg.prgramming6.warehouse.domain;

public class LicensePlate {
    private final String plateNumber;

    public LicensePlate(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    @Override
    public String toString() {
        return plateNumber;
    }
}