// landside/src/main/java/be/kdg/prgramming6/landside/domain/LicensePlate.java
package be.kdg.prgramming6.landside.domain;

public record LicensePlate(String value) {
    public LicensePlate {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}