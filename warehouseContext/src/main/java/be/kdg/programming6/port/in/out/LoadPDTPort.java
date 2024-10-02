package be.kdg.programming6.port.in.out;

import be.kdg.programming6.domain.PayloadDeliveryTicket;
import be.kdg.programming6.domain.LicensePlate;

import java.util.Optional;

public interface LoadPDTPort {

    /**
     * Load a Payload Delivery Ticket by the truck's license plate.
     *
     * @param licensePlate The license plate of the truck to load.
     * @return An Optional containing the Payload Delivery Ticket if found, or empty if not found.
     */
    Optional<PayloadDeliveryTicket> loadPDTByPlate(LicensePlate licensePlate);
}
