package be.kdg.prgramming6.landside.domain;

import java.util.Optional;

public class Weighbridge {
    private final WeighbridgeNumber weighbridgeNumber;
    private final LicensePlate licensePlate;
    private Truck currentTruck;

    public Weighbridge(WeighbridgeNumber weighbridgeNumber, LicensePlate licensePlate) {
        this.weighbridgeNumber = weighbridgeNumber;
        this.licensePlate = licensePlate;
    }

    public Optional<Truck> getCurrentTruck() {
        return Optional.ofNullable(currentTruck);
    }

    public void setCurrentTruck(Truck truck) {
        this.currentTruck = truck;
    }

    public WeighbridgeNumber getWeighbridgeNumber() {
        return weighbridgeNumber;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }
}