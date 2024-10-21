// landside/src/main/java/be/kdg/prgramming6/landside/adapters/in/web/TruckDTO.java
package be.kdg.prgramming6.landside.adapters.in;

public class TruckDTO {
    private final String licensePlate;

    public TruckDTO(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}