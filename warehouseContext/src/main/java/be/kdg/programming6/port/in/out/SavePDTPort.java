package be.kdg.programming6.port.in.out;

import be.kdg.programming6.domain.PayloadDeliveryTicket;
import be.kdg.programming6.domain.LicensePlate;
import be.kdg.programming6.domain.MaterialType;
import be.kdg.programming6.domain.WarehouseId;

import java.time.LocalDateTime;

public interface SavePDTPort {
    /**
     * Save a new Payload Delivery Ticket.
     *
     * @param licensePlate The license plate of the truck.
     * @param materialType The type of material being delivered.
     * @param warehouseId The ID of the warehouse receiving the delivery.
     * @param dockNumber The dock number where the truck will be docked.
     * @param deliveryDate The date and time of the delivery.
     * @return The saved Payload Delivery Ticket.
     */
    PayloadDeliveryTicket savePDT(LicensePlate licensePlate, MaterialType materialType, WarehouseId warehouseId, String dockNumber, LocalDateTime deliveryDate);
}
