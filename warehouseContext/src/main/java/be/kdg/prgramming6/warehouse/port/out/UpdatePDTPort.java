package be.kdg.prgramming6.warehouse.port.out;

import java.time.LocalDateTime;
import java.util.UUID;

@FunctionalInterface
public interface UpdatePDTPort {
    void updatePDT(String licensePlate, String materialType, UUID warehouseId, String conveyorBeltId, LocalDateTime deliveryDate);
}
