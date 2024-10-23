package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.PayloadDeliveryTicket;
import be.kdg.prgramming6.warehouse.domain.LicensePlate;
import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;

import java.time.LocalDateTime;

@FunctionalInterface
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
