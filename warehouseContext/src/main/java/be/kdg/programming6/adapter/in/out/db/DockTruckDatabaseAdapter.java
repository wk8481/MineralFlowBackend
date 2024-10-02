package be.kdg.programming6.adapter.in.out.db;

import be.kdg.programming6.domain.*;
import be.kdg.programming6.port.in.out.LoadPDTPort;
import be.kdg.programming6.port.in.out.LoadWarehousePort;
import be.kdg.programming6.port.in.out.SavePDTPort;
import be.kdg.programming6.port.in.out.UpdatePDTPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class DockTruckDatabaseAdapter implements LoadWarehousePort, UpdatePDTPort, LoadPDTPort, SavePDTPort {
    private final WarehouseJpaRepository warehouseJpaRepository;
    private final PayloadDeliveryTicketJpaRepository payloadDeliveryTicketJpaRepository;

    public DockTruckDatabaseAdapter(WarehouseJpaRepository warehouseJpaRepository, PayloadDeliveryTicketJpaRepository payloadDeliveryTicketJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.payloadDeliveryTicketJpaRepository = payloadDeliveryTicketJpaRepository;
    }

    @Override
    public WarehouseId findWarehouseIdbySellerId(UUID sellerId) {
        // Find the warehouse entity by sellerId
        Optional<WarehouseJpaEntity> warehouseEntity = Optional.ofNullable(warehouseJpaRepository.findBySellerId(sellerId));

        // Map the WarehouseJpaEntity to WarehouseId if found
        return warehouseEntity.map(entity -> new WarehouseId(entity.getWarehouseId())).orElse(null);
    }

    @Override
    public void updatePDT(String licensePlate, String materialType, UUID warehouseId, String conveyorBeltId, LocalDateTime deliveryDate) {
        // Create a new PayloadDeliveryTicketJpaEntity with the updated values
        PayloadDeliveryTicketJpaEntity updatedPDT = new PayloadDeliveryTicketJpaEntity(
                warehouseId,
                licensePlate,
                MaterialType.valueOf(materialType), // Ensure materialType is a valid enum
                conveyorBeltId,
                deliveryDate
        );

        // Persist the updated Payload Delivery Ticket entity
        payloadDeliveryTicketJpaRepository.save(updatedPDT);

        // Optionally, log the update
        System.out.println("Updated Payload Delivery Ticket for license plate: " + licensePlate);
    }

    private PayloadDeliveryTicket toPayloadDeliveryTicket(PayloadDeliveryTicketJpaEntity pdtEntity) {
        return new PayloadDeliveryTicket(
                new LicensePlate(pdtEntity.getLicensePlate()),
                pdtEntity.getMaterialType(),
                pdtEntity.getDeliveryDate(),
                new WarehouseId(pdtEntity.getWarehouseId()),
                pdtEntity.getDockNumber()
        );
    }

    @Override
    public Optional<PayloadDeliveryTicket> loadPDTByPlate(LicensePlate licensePlate) {
        // Load the Payload Delivery Ticket from the repository
        Optional<PayloadDeliveryTicketJpaEntity> pdtEntity = payloadDeliveryTicketJpaRepository.findByLicensePlate(licensePlate.toString());

        // Map the entity to the domain model and return
        return pdtEntity.map(this::toPayloadDeliveryTicket);
    }

    @Override
    public PayloadDeliveryTicket savePDT(LicensePlate licensePlate, MaterialType materialType, WarehouseId warehouseId, String dockNumber, LocalDateTime deliveryDate) {
        // Create a new PayloadDeliveryTicketJpaEntity for saving
        PayloadDeliveryTicketJpaEntity newPDT = new PayloadDeliveryTicketJpaEntity(
                warehouseId.id(), // Use getId() to retrieve the UUID of the WarehouseId
                licensePlate.toString(),
                materialType,
                dockNumber,
                deliveryDate
        );

        // Save the new Payload Delivery Ticket entity
        PayloadDeliveryTicketJpaEntity savedEntity = payloadDeliveryTicketJpaRepository.save(newPDT);

        // Map the saved entity back to the domain model and return
        return toPayloadDeliveryTicket(savedEntity);
    }
}
