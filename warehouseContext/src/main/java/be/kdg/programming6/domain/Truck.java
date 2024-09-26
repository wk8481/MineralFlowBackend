package be.kdg.programming6.domain;

public record Truck(LicensePlate licensePlate, MaterialType materialType) {
    public Truck {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null");
        }
    }

    @Override
    public LicensePlate licensePlate() {
        return licensePlate;
    }

    @Override
    public MaterialType materialType() {
        return materialType;
    }


}
