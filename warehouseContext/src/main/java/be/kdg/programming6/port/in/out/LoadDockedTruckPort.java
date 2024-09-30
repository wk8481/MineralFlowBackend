package be.kdg.programming6.port.in.out;

import be.kdg.programming6.domain.Truck;
import be.kdg.programming6.domain.LicensePlate;

import java.util.Optional;

public interface LoadDockedTruckPort {

    /**
     * Load a docked truck by its license plate.
     *
     * @param licensePlate The license plate of the truck to load.
     * @return An Optional containing the Truck if found, or empty if not found.
     */
    Optional<Truck> loadTruckByPlate(LicensePlate licensePlate);
}
