package be.kdg.prgramming6.landside.domain;

public record WeighbridgeNumber(String value) {
    public WeighbridgeNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Weighbridge number cannot be null or blank");
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public static WeighbridgeNumber generate(LicensePlate licensePlate) {
        String generatedValue = licensePlate.toString() + "-WB";
        return new WeighbridgeNumber(generatedValue);
    }
}