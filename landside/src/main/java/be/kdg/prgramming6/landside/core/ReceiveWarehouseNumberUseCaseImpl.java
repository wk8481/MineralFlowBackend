//package be.kdg.prgramming6.landside.core;
//
//import be.kdg.prgramming6.landside.domain.LicensePlate;
//import be.kdg.prgramming6.landside.domain.Weighbridge;
//import be.kdg.prgramming6.landside.domain.WeighbridgeNumber;
//import be.kdg.prgramming6.landside.domain.Truck;
//import be.kdg.prgramming6.landside.domain.Warehouse;
//import be.kdg.prgramming6.landside.domain.WarehouseId;
//import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberCommand;
//import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberUseCase;
//import be.kdg.prgramming6.landside.port.out.LoadWarehousePort;
//import be.kdg.prgramming6.landside.port.out.LoadTruckPort;
//import be.kdg.prgramming6.landside.port.out.LoadWeighbridgePort;
//import be.kdg.prgramming6.landside.port.out.UpdateTruckPort;
//import be.kdg.prgramming6.landside.port.out.UpdateWeighbridgePort;
//import be.kdg.prgramming6.landside.port.out.UpdateWarehousePort;
//
//import java.math.BigDecimal;
//
//public class ReceiveWarehouseNumberUseCaseImpl implements ReceiveWarehouseNumberUseCase {
//    private final UpdateWeighbridgePort updateWeighbridgePort;
//    private final UpdateWarehousePort updateWarehousePort;
//    private final UpdateTruckPort updateTruckPort;
//    private final LoadWarehousePort loadWarehousePort;
//    private final LoadTruckPort loadTruckPort;
//    private final LoadWeighbridgePort loadWeighbridgePort;
//
//    public ReceiveWarehouseNumberUseCaseImpl(UpdateWeighbridgePort updateWeighbridgePort,
//                                             UpdateWarehousePort updateWarehousePort,
//                                             UpdateTruckPort updateTruckPort,
//                                             LoadWarehousePort loadWarehousePort,
//                                             LoadTruckPort loadTruckPort,
//                                             LoadWeighbridgePort loadWeighbridgePort) {
//        this.updateWeighbridgePort = updateWeighbridgePort;
//        this.updateWarehousePort = updateWarehousePort;
//        this.updateTruckPort = updateTruckPort;
//        this.loadWarehousePort = loadWarehousePort;
//        this.loadTruckPort = loadTruckPort;
//        this.loadWeighbridgePort = loadWeighbridgePort;
//    }
//
//    @Override
//    public WarehouseId receiveWarehouseNumber(ReceiveWarehouseNumberCommand command) {
//        // Load the truck by license plate
//        Truck truck = loadTruckPort.loadTruck(command.licensePlate());
//        if (truck == null) {
//            throw new IllegalArgumentException("Truck not found with license plate: " + command.licensePlate());
//        }
//
//        // Load the weighbridge by weighbridge number
//        Weighbridge weighbridge = loadWeighbridgePort.loadWeighbridge(command.weighbridgeNumber());
//        if (weighbridge == null) {
//            throw new IllegalArgumentException("Weighbridge not found with number: " + command.weighbridgeNumber());
//        }
//
//        // Load the warehouse by warehouse ID associated with the truck
//        Warehouse warehouse = loadWarehousePort.loadWarehouse(truck.getWarehouseId());
//        if (warehouse == null) {
//            throw new IllegalArgumentException("Warehouse not found for truck with ID: " + truck.getWarehouseId());
//        }
//
//        // Update the truck with the new payload weight directly
//        truck.setPayloadWeight(command.payloadWeight()); // Assuming a setter exists for payload weight
//        updateTruckPort.updateTruck(truck);
//
//        // Update the weighbridge with the truck information if needed
//        weighbridge.updateWithTruck(truck); // Assuming a method to update weighbridge with truck details
//        updateWeighbridgePort.updateWeighbridge(weighbridge);
//
//        // Update the warehouse if needed (you can customize this based on your logic)
//        updateWarehousePort.updateWarehouse(warehouse);
//
//        // Return the warehouse ID
//        return warehouse.getWarehouseId();
//    }
//}
