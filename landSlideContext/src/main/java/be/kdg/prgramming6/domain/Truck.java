package be.kdg.prgramming6.domain;

public record Truck(LicensePlate licensePlate) {
    public Truck {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
    }
}
