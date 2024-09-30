package be.kdg.programming6.adapter.in.out.db;

import be.kdg.programming6.domain.PayloadDeliveryTicket;
import be.kdg.programming6.domain.WarehouseId;
import be.kdg.programming6.domain.WeighbridgeNumber;
import be.kdg.programming6.port.in.out.LoadWarehousePort;
import be.kdg.programming6.port.in.out.UpdateWarehousePort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DockTruckDatabaseAdapter implements LoadWarehousePort {
    private final WarehouseJpaRepository warehouseJpaRepository;

    public DockTruckDatabaseAdapter(WarehouseJpaRepository warehouseJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
    }

    @Override
    public WarehouseId findWarehouseIdbySellerId(UUID sellerId) {
        // Find the warehouse entity by sellerId
        Optional<WarehouseJpaEntity> warehouseEntity = Optional.ofNullable(warehouseJpaRepository.findBySellerId(sellerId));

        // Map the WarehouseJpaEntity to WarehouseId if found
        return warehouseEntity.map(entity -> new WarehouseId(entity.getWarehouseId())).orElse(null);
    }

    // Method to save PDT (Payload Delivery Ticket)
    public void savePDT(PayloadDeliveryTicket pdt) {
        // Implement logic to save the Payload Delivery Ticket to the database
        System.out.println("Saving PDT to database: " + pdt.toString());
    }

    // Method to save Weighbridge Number
    public void saveWeighbridgeNumber(WeighbridgeNumber weighbridgeNumber) {
        System.out.println("Saving Weighbridge Number to database: " + weighbridgeNumber.toString());
    }

//    @Override
//    public void updateWarehouse(WarehouseId warehouseId, String weighbridgeNumber, String pdt) {
//        // Logic to update the warehouse entity in the database
//        Optional<WarehouseJpaEntity> warehouseEntity = warehouseJpaRepository.findById(warehouseId.id());
//
//        if (warehouseEntity.isPresent()) {
//            WarehouseJpaEntity entity = warehouseEntity.get();
//            // Assuming you have appropriate setters in your WarehouseJpaEntity
//            entity.setWeighbridgeNumber(weighbridgeNumber); // Set the weighbridge number
//            entity.setPayloadDeliveryTicket(pdt); // Set the PDT as string
//
//            // Save the updated warehouse entity back to the database
//            warehouseJpaRepository.save(entity);
//            System.out.println("Updated warehouse with ID: " + warehouseId + " with new weighbridge number and PDT.");
//        } else {
//            throw new IllegalArgumentException("Warehouse not found for ID: " + warehouseId);
//        }
    }


