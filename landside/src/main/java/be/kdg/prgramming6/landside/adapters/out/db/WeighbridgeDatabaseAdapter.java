package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class WeighbridgeDatabaseAdapter implements LoadWarehousePort, LoadTruckPort, LoadWeighbridgePort, UpdateWeighbridgePort, UpdateWarehousePort, UpdateTruckPort {

    private final WarehouseProjectionJpaRepository warehouseProjectionJpaRepository;
    private final TruckJpaRepository truckJpaRepository;
    private final WeighbridgeJpaRepository weighbridgeJpaRepository;

    public WeighbridgeDatabaseAdapter(WarehouseProjectionJpaRepository warehouseProjectionJpaRepository,
                                      TruckJpaRepository truckJpaRepository,
                                      WeighbridgeJpaRepository weighbridgeJpaRepository) {
        this.warehouseProjectionJpaRepository = warehouseProjectionJpaRepository;
        this.truckJpaRepository = truckJpaRepository;
        this.weighbridgeJpaRepository = weighbridgeJpaRepository;
    }


    @Override
    public Optional<Truck> loadTruck(LicensePlate licensePlate) {
        TruckJpaEntity truckJpaEntity = truckJpaRepository.findByTruckNumber(licensePlate.toString());
        return Optional.ofNullable(toTruck(truckJpaEntity));
    }

    @Override
    public Optional<Weighbridge> loadWeighbridge(WeighbridgeNumber weighbridgeNumber) {
        WeighbridgeJpaEntity weighbridgeJpaEntity = weighbridgeJpaRepository.findByWeighbridgeNumber(weighbridgeNumber.getValue());
        return Optional.ofNullable(toWeighbridge(weighbridgeJpaEntity));
    }

    @Override
    public Optional<Warehouse> loadWarehouse(SellerId sellerId, MaterialType materialType, LocalDateTime timestamp) {
        WarehouseProjectionJpaEntity warehouseProjectionJpaEntity = warehouseProjectionJpaRepository
                .findFirstBySellerIdAndMaterialTypeAndTimestampLessThanEqualOrderByTimestampDesc(
                        sellerId.getValue(),
                        materialType.toString(),
                        timestamp
                ).orElse(null);
        return Optional.ofNullable(toWarehouse(warehouseProjectionJpaEntity));
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


    // Transform TruckJpaEntity to Truck
    private Truck toTruck(TruckJpaEntity truckJpaEntity) {
        if (truckJpaEntity == null) {
            return null;
        }
        return new Truck(
                new LicensePlate(truckJpaEntity.getLicensePlate()),
                truckJpaEntity.getPayloadWeight(),
                truckJpaEntity.getMaterialType(),
                truckJpaEntity.getDockNumber(),
                new SellerId(truckJpaEntity.getSellerId())
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
                warehouse.getSellerId().getValue()
        );
    }

    // Transform Truck to TruckJpaEntity
    private TruckJpaEntity fromTruck(Truck truck) {
        return new TruckJpaEntity(
                truck.getLicensePlate().toString(),
                truck.getPayloadWeight(),
                truck.getMaterialType(),
                truck.getDockNumber(),
                truck.getSellerId().toString()
        );
    }

    // Transform Weighbridge to WeighbridgeJpaEntity
    private WeighbridgeJpaEntity fromWeighbridge(Weighbridge weighbridge) {
        return new WeighbridgeJpaEntity(weighbridge.getWeighbridgeNumber().toString());
    }

    public void updateTruck(Truck truck) {
        TruckJpaEntity truckJpaEntity = fromTruck(truck);
        truckJpaRepository.save(truckJpaEntity);
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        WarehouseProjectionJpaEntity warehouseProjectionJpaEntity = fromWarehouse(warehouse);
        warehouseProjectionJpaRepository.save(warehouseProjectionJpaEntity);
    }

    @Override
    public void updateWeighbridge(Weighbridge weighbridge) {
        WeighbridgeJpaEntity weighbridgeJpaEntity = fromWeighbridge(weighbridge);
        weighbridgeJpaRepository.save(weighbridgeJpaEntity);
    }
}
