package be.kdg.programming6.port.in.out;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UpdatePDTPort {
    void updatePDT(String licensePlate, String materialType, UUID warehouseId, String conveyorBeltId, LocalDateTime deliveryDate);
}
