package be.kdg.prgramming6.warehouse.adapter.in.out.db;

import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.out.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DockTruckDatabaseAdapter implements LoadWarehouseBySellerAndMaterialPort, UpdatePDTPort, LoadPDTPort, SavePDTPort, UpdateWarehousePort, LoadWarehouseByIdPort{
    private final WarehouseJpaRepository warehouseJpaRepository;
    private final PayloadDeliveryTicketJpaRepository payloadDeliveryTicketJpaRepository;
    private final WarehouseActivityJpaRepository warehouseActivityJpaRepository;

    public DockTruckDatabaseAdapter(WarehouseJpaRepository warehouseJpaRepository, PayloadDeliveryTicketJpaRepository payloadDeliveryTicketJpaRepository, WarehouseActivityJpaRepository warehouseActivityJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.payloadDeliveryTicketJpaRepository = payloadDeliveryTicketJpaRepository;
        this.warehouseActivityJpaRepository = warehouseActivityJpaRepository;
    }




    @Override
    public WarehouseId findWarehouseIdBySellerIdAndMaterialType(UUID sellerId, MaterialType materialType) {
        // Find the warehouse entity by sellerId and materialType
        Optional<WarehouseJpaEntity> warehouseEntity = warehouseJpaRepository.findBySellerIdAndMaterialType(sellerId, materialType);

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

    @Override
    public void activityCreated(Warehouse warehouse, WarehouseActivity activity) {
        final UUID warehouseId = warehouse.getWarehouseId().id();
        final WarehouseJpaEntity warehouseEntity = warehouseJpaRepository.findByWarehouseId(warehouseId).orElseThrow();
        warehouseEntity.getActivities().add(toWarehouseActivity(warehouseEntity, activity));
        warehouseJpaRepository.save(warehouseEntity);


    }

    private WarehouseActivityJpaEntity toWarehouseActivity(final WarehouseJpaEntity warehouseJpaEntity,
                                                           final WarehouseActivity activity) {
        final WarehouseActivityJpaEntity warehouseActivityJpaEntity = new WarehouseActivityJpaEntity();
        warehouseActivityJpaEntity.setId(WarehouseActivityJpaId.of(activity.activityId()));
        warehouseActivityJpaEntity.setType(activity.warehouseActivityType());
        warehouseActivityJpaEntity.setWeight(activity.weight());
        warehouseActivityJpaEntity.setTime(activity.time());
        warehouseActivityJpaEntity.setSellerId(activity.sellerId().id());
        warehouseActivityJpaEntity.setMaterialType(activity.materialType());
        warehouseActivityJpaEntity.setWarehouse(warehouseJpaEntity);
        return warehouseActivityJpaEntity;
    }

    private Warehouse toWarehouse(final WarehouseJpaEntity warehouseJpaEntity){
        final WarehouseId warehouseId = new WarehouseId(warehouseJpaEntity.getWarehouseId());
        final WarehouseActivityWindow activities = toWarehouseWindow(warehouseId);
        return new Warehouse(warehouseId, activities);
    }

    private WarehouseActivityWindow toWarehouseWindow(final WarehouseId warehouseId) {
        final List<WarehouseActivity> activities = warehouseActivityJpaRepository
            .findAllById_WarehouseId(warehouseId.id())
            .stream()
            .map(DockTruckDatabaseAdapter::toWarehouseActivity)
            .collect(Collectors.toList());
        return new WarehouseActivityWindow(warehouseId, activities);
    }

    private static WarehouseActivity toWarehouseActivity(final WarehouseActivityJpaEntity activity) {
        return new WarehouseActivity(
            new WarehouseActivityId(new WarehouseId(activity.getId().getWarehouseId()), activity.getId().getActivityId()),
            activity.getTime(),
            activity.getType(),
            new SellerId(activity.getSellerId()),
            activity.getMaterialType(),
            activity.getWeight()


        );
    }

    @Override
    public Optional<Warehouse> loadWarehouseById(WarehouseId warehouseId) {
        return warehouseJpaRepository.findByWarehouseId(warehouseId.id()).map(this::toWarehouse);
    }
}
