package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class WeighbridgeDatabaseAdapter implements LoadWarehousePort, LoadWeighbridgePort, UpdateWeighbridgePort, UpdateWarehousePort {

    private final WarehouseProjectionJpaRepository warehouseProjectionJpaRepository;
    private final WeighbridgeJpaRepository weighbridgeJpaRepository;

    public WeighbridgeDatabaseAdapter(WarehouseProjectionJpaRepository warehouseProjectionJpaRepository,
                                      WeighbridgeJpaRepository weighbridgeJpaRepository) {
        this.warehouseProjectionJpaRepository = warehouseProjectionJpaRepository;
        this.weighbridgeJpaRepository = weighbridgeJpaRepository;
    }

    @Override
    public Optional<Weighbridge> loadWeighbridge(WeighbridgeNumber weighbridgeNumber) {
        WeighbridgeJpaEntity weighbridgeJpaEntity = weighbridgeJpaRepository.findByWeighbridgeNumber(weighbridgeNumber.toString());
        return Optional.ofNullable(toWeighbridge(weighbridgeJpaEntity));
    }

    private Weighbridge toWeighbridge(WeighbridgeJpaEntity weighbridgeJpaEntity) {
        if (weighbridgeJpaEntity == null) {
            return null;
        }
        WeighbridgeNumber weighbridgeNumber = new WeighbridgeNumber(weighbridgeJpaEntity.getWeighbridgeNumber());
        LicensePlate licensePlate = new LicensePlate(weighbridgeJpaEntity.getLicensePlate());
        return new Weighbridge(weighbridgeNumber, licensePlate);
    }

    private WeighbridgeJpaEntity fromWeighbridge(Weighbridge weighbridge) {
        return new WeighbridgeJpaEntity(
                weighbridge.getWeighbridgeNumber().toString(),
                weighbridge.getLicensePlate().plateNumber()
        );
    }

    @Override
    public void updateWeighbridge(Weighbridge weighbridge) {
        WeighbridgeJpaEntity weighbridgeJpaEntity = fromWeighbridge(weighbridge);
        weighbridgeJpaRepository.save(weighbridgeJpaEntity);
    }

    @Override
    public Optional<Warehouse> loadWarehouse(SellerId sellerId, MaterialType materialType, LocalDateTime timestamp) {
        return Optional.of(warehouseProjectionJpaRepository.findFirstBySellerIdAndMaterialTypeAndTimestampLessThanEqualOrderByTimestampDesc(
                        sellerId.id(),
                        materialType,
                        timestamp)
                .map(warehouseProjection -> new Warehouse(
                        new WarehouseId(warehouseProjection.getWarehouseId()),
                        warehouseProjection.getMaterialType(),
                        new SellerId(warehouseProjection.getSellerId()),
                        warehouseProjection.getCurrentCapacity()))
                .orElseGet(() -> new Warehouse(new WarehouseId(UUID.randomUUID()), materialType, sellerId, BigDecimal.ZERO)));
    }

    @Override
    public void updateWarehouse(final Warehouse warehouse) {
        final WarehouseProjectionJpaEntity warehouseProjectionJpaEntity = warehouseProjectionJpaRepository.findFirstBySellerIdAndMaterialTypeAndTimestampLessThanEqualOrderByTimestampDesc(
                        warehouse.getSellerId().getValue(),
                        warehouse.getMaterialType(),
                        LocalDateTime.now())
                .orElseGet(() -> fromWarehouse(warehouse));
        warehouseProjectionJpaEntity.setCurrentCapacity(warehouse.getCurrentCapacity());
        warehouseProjectionJpaRepository.save(warehouseProjectionJpaEntity);
    }

    private WarehouseProjectionJpaEntity fromWarehouse(Warehouse warehouse) {
        return new WarehouseProjectionJpaEntity(
                warehouse.getWarehouseId().getValue(),
                warehouse.getMaterialType(),
                warehouse.getSellerId().getValue(),
                LocalDateTime.now(),
                warehouse.getCurrentCapacity()
        );
    }
}