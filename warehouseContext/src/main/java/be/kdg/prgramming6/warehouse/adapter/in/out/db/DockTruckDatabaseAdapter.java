package be.kdg.prgramming6.warehouse.adapter.in.out.db;

import be.kdg.prgramming6.warehouse.domain.*;
import be.kdg.prgramming6.warehouse.port.in.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DockTruckDatabaseAdapter implements LoadWarehouseBySellerAndMaterialPort, UpdatePDTPort, LoadPDTPort, SavePDTPort, UpdateWarehousePort, LoadWarehouseByIdPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(DockTruckDatabaseAdapter.class);

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
        Optional<WarehouseJpaEntity> warehouseEntity = warehouseJpaRepository.findBySellerIdAndMaterialType(sellerId, materialType);
        return warehouseEntity.map(entity -> new WarehouseId(entity.getWarehouseId())).orElse(null);
    }

    @Override
    public void updatePDT(String licensePlate, String materialType, UUID warehouseId, String conveyorBeltId, LocalDateTime deliveryDate) {
        PayloadDeliveryTicketJpaEntity updatedPDT = new PayloadDeliveryTicketJpaEntity(
                warehouseId,
                licensePlate,
                MaterialType.valueOf(materialType),
                conveyorBeltId,
                deliveryDate
        );
        payloadDeliveryTicketJpaRepository.save(updatedPDT);
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
        Optional<PayloadDeliveryTicketJpaEntity> pdtEntity = payloadDeliveryTicketJpaRepository.findByLicensePlate(licensePlate.toString());
        return pdtEntity.map(this::toPayloadDeliveryTicket);
    }

    @Override
    public PayloadDeliveryTicket savePDT(LicensePlate licensePlate, MaterialType materialType, WarehouseId warehouseId, String dockNumber, LocalDateTime deliveryDate) {
        PayloadDeliveryTicketJpaEntity newPDT = new PayloadDeliveryTicketJpaEntity(
                warehouseId.id(),
                licensePlate.toString(),
                materialType,
                dockNumber,
                deliveryDate
        );
        PayloadDeliveryTicketJpaEntity savedEntity = payloadDeliveryTicketJpaRepository.save(newPDT);
        return toPayloadDeliveryTicket(savedEntity);
    }


    @Override
    public void activityCreated(Warehouse warehouse, WarehouseActivity activity) {
        final UUID warehouseId = warehouse.getWarehouseId().id();
        final WarehouseJpaEntity warehouseEntity = warehouseJpaRepository.findByWarehouseId(warehouseId).orElseThrow();
        warehouseEntity.getActivities().add(toWarehouseActivity(warehouseEntity, activity));
        LOGGER.info("Updated warehouse for {} with new activity", warehouseId);
        warehouseJpaRepository.save(warehouseEntity);
    }

    private WarehouseActivityJpaEntity toWarehouseActivity(final WarehouseJpaEntity warehouseJpaEntity, final WarehouseActivity activity) {
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

    private Warehouse toWarehouse(final WarehouseJpaEntity warehouseJpaEntity) {
        final WarehouseId warehouseId = new WarehouseId(warehouseJpaEntity.getWarehouseId());
        final SellerId sellerId = new SellerId(warehouseJpaEntity.getSellerId());
        final MaterialType materialType = warehouseJpaEntity.getMaterialType();
        final WarehouseActivityWindow activities = toWarehouseWindow(warehouseJpaEntity, warehouseId);
        return new Warehouse(warehouseId, sellerId, materialType, activities);
    }

    private WarehouseActivityWindow toWarehouseWindow(final WarehouseJpaEntity warehouseJpaEntity, final WarehouseId warehouseId) {
        final List<WarehouseActivity> activities = warehouseActivityJpaRepository
                .findAllById_WarehouseIdAndTimeAfter(warehouseId.id(), LocalDateTime.now())
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