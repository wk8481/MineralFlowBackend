package be.kdg.prgramming6.warehouse.port.in.out;

import be.kdg.prgramming6.warehouse.domain.PayloadDeliveryTicket;
import be.kdg.prgramming6.warehouse.domain.LicensePlate;

import java.util.Optional;

@FunctionalInterface
public interface LoadPDTPort {

    /**
     * Load a Payload Delivery Ticket by the truck's license plate.
     *
     * @param licensePlate The license plate of the truck to load.
     * @return An Optional containing the Payload Delivery Ticket if found, or empty if not found.
     */
    Optional<PayloadDeliveryTicket> loadPDTByPlate(LicensePlate licensePlate);
}
