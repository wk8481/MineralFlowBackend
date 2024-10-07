package be.kdg.prgramming6.landside.domain;

public record Truck(LicensePlate licensePlate) {
    public Truck {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }
}
