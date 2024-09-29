package be.kdg.programming6.adapter.in;

public record DockTruckDTO(
        String licensePlate,
        String materialType,
        String warehouseId,
        String conveyorBeltId,
        String weighingBridgeNumber,
        String deliveryDate
) {}