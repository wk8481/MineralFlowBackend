package be.kdg.programming6.port.in.out;

public interface UpdateDockedTruckPort {
    void updateDockedTruck(String licensePlate, String materialType, String warehouseId, String conveyorBeltId, String weighingBridgeNumber, String deliveryDate);
}
