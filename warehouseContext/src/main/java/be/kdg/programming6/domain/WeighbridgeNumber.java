package be.kdg.programming6.domain;

public record WeighbridgeNumber(String value) {

    // Primary constructor with validation logic
    public WeighbridgeNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Weighbridge number cannot be null or blank");
        }
    }

    // Static factory method to generate a unique weighbridge number
    public static WeighbridgeNumber generate(Truck truck) {
        String uniqueNumber = "WB-" + truck.getLicensePlate().plateNumber() + "-" + System.nanoTime();
        return new WeighbridgeNumber(uniqueNumber);
    }

    @Override
    public String toString() {
        return value;
    }
}
