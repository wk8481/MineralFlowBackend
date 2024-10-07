package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.out.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeighbridgeDatabaseAdapter implements LoadWarehousePort, LoadTruckPort, LoadWeighbridgePort, UpdateWeighbridgePort, UpdateWarehousePort, UpdateTruckPort {

    private final WarehouseJpaRepository warehouseJpaRepository;
    private final TruckJpaRepository truckJpaRepository;
    private final WeighbridgeJpaRepository weighbridgeJpaRepository;

    public WeighbridgeDatabaseAdapter(WarehouseJpaRepository warehouseJpaRepository,
                                      TruckJpaRepository truckJpaRepository,
                                      WeighbridgeJpaRepository weighbridgeJpaRepository) {
        this.warehouseJpaRepository = warehouseJpaRepository;
        this.truckJpaRepository = truckJpaRepository;
        this.weighbridgeJpaRepository = weighbridgeJpaRepository;
    }

    @Override
    public Optional<Warehouse> loadWarehouse(WarehouseId warehouseId) {
        WarehouseJpaEntity warehouseJpaEntity = warehouseJpaRepository.findByWarehouseNumber(warehouseId.id());
        return Optional.ofNullable(toWarehouse(warehouseJpaEntity));
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

    // Transform WarehouseJpaEntity to Warehouse
    private Warehouse toWarehouse(WarehouseJpaEntity warehouseJpaEntity) {
        if (warehouseJpaEntity == null) {
            return null;
        }
        return new Warehouse(
                new WarehouseId(warehouseJpaEntity.getWarehouseId()),
                warehouseJpaEntity.getMaterialType(),
                new SellerId(warehouseJpaEntity.getSellerId())  // Assuming SellerId is stored in warehouseJpaEntity
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
                truckJpaEntity.getMaterialType()
        );
    }

    // Transform WeighbridgeJpaEntity to Weighbridge
    private Weighbridge toWeighbridge(WeighbridgeJpaEntity weighbridgeJpaEntity) {
        if (weighbridgeJpaEntity == null) {
            return null;
        }
        // Convert String to WeighbridgeNumber
        WeighbridgeNumber weighbridgeNumber = new WeighbridgeNumber(weighbridgeJpaEntity.getWeighbridgeNumber());
        return new Weighbridge(weighbridgeNumber);
    }

    // Transform Warehouse to WarehouseJpaEntity
    // Transform Warehouse to WarehouseJpaEntity
    private WarehouseJpaEntity fromWarehouse(Warehouse warehouse) {
        return new WarehouseJpaEntity(
                warehouse.getWarehouseId().getValue(), // Ensure this returns the UUID or appropriate type
                warehouse.getMaterialType(),
                warehouse.getSellerId().getValue() // Assuming SellerId is a type that has a getValue() method
        );
    }


    // Transform Truck to TruckJpaEntity
    private TruckJpaEntity fromTruck(Truck truck) {
        return new TruckJpaEntity(
                truck.getLicensePlate().toString(),
                truck.getPayloadWeight(),
                truck.getMaterialType()
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
        WarehouseJpaEntity warehouseJpaEntity = fromWarehouse(warehouse);
        warehouseJpaRepository.save(warehouseJpaEntity);
    }

    @Override
    public void updateWeighbridge(Weighbridge weighbridge) {
        WeighbridgeJpaEntity weighbridgeJpaEntity = fromWeighbridge(weighbridge);
        weighbridgeJpaRepository.save(weighbridgeJpaEntity);
    }
}
