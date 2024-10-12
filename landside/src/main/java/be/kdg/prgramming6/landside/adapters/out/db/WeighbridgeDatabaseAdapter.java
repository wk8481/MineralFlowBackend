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
        WeighbridgeJpaEntity weighbridgeJpaEntity = weighbridgeJpaRepository.findByWeighbridgeNumber(weighbridgeNumber.getValue());
        return Optional.ofNullable(toWeighbridge(weighbridgeJpaEntity));
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

    // Transform WarehouseJpaEntity to Warehouse
    private Warehouse toWarehouse(WarehouseProjectionJpaEntity warehouseProjectionJpaEntity) {
        if (warehouseProjectionJpaEntity == null) {
            return null;
        }
        return new Warehouse(
                new WarehouseId(warehouseProjectionJpaEntity.getWarehouseId()),
                warehouseProjectionJpaEntity.getMaterialType(),
                new SellerId(warehouseProjectionJpaEntity.getSellerId())
        );
    }




    // Transform WeighbridgeJpaEntity to Weighbridge
    private Weighbridge toWeighbridge(WeighbridgeJpaEntity weighbridgeJpaEntity) {
        if (weighbridgeJpaEntity == null) {
            return null;
        }
        WeighbridgeNumber weighbridgeNumber = new WeighbridgeNumber(weighbridgeJpaEntity.getWeighbridgeNumber());
        return new Weighbridge(weighbridgeNumber);
    }

    // Transform Warehouse to WarehouseJpaEntity
    private WarehouseProjectionJpaEntity fromWarehouse(Warehouse warehouse) {
        return new WarehouseProjectionJpaEntity(
                warehouse.getWarehouseId().getValue(), // Ensure this returns the UUID or appropriate type
                warehouse.getMaterialType(),
                warehouse.getSellerId().getValue(),
                LocalDateTime.now(), // Use the current timestamp
                warehouse.getCurrentCapacity()
        );
    }




    // Transform Weighbridge to WeighbridgeJpaEntity
    private WeighbridgeJpaEntity fromWeighbridge(Weighbridge weighbridge) {
        return new WeighbridgeJpaEntity(weighbridge.getWeighbridgeNumber().toString());
    }


    @Override
    public void updateWarehouse(final Warehouse warehouse) {
       final WarehouseProjectionJpaEntity
               warehouseProjectionJpaEntity = warehouseProjectionJpaRepository.findFirstBySellerIdAndMaterialTypeAndTimestampLessThanEqualOrderByTimestampDesc(
                warehouse.getSellerId().getValue(),
                warehouse.getMaterialType(),
                LocalDateTime.now())
               .orElseGet(() -> fromWarehouse(warehouse));
       warehouseProjectionJpaEntity.setCurrentCapacity(warehouse.getCurrentCapacity());
        warehouseProjectionJpaRepository.save(warehouseProjectionJpaEntity);
    }

    @Override
    public void updateWeighbridge(Weighbridge weighbridge) {
        WeighbridgeJpaEntity weighbridgeJpaEntity = fromWeighbridge(weighbridge);
        weighbridgeJpaRepository.save(weighbridgeJpaEntity);
    }
}
