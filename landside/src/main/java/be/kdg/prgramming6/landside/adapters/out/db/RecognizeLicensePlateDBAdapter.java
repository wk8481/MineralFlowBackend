package be.kdg.prgramming6.landside.adapters.out.db;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.MaterialType;
import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.port.out.LoadTruckPort;
import be.kdg.prgramming6.landside.port.out.SaveTruckPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecognizeLicensePlateDBAdapter implements LoadTruckPort, SaveTruckPort {
    private final TruckJpaRepository truckRepository;

    public RecognizeLicensePlateDBAdapter(TruckJpaRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    private Truck mapToDomain(TruckJpaEntity entity) {
        return new Truck(
                new LicensePlate(entity.getLicensePlate()),
                MaterialType.valueOf(entity.getMaterialType()),
                entity.getDockNumber()
        );
    }

    @Override
    public Optional<Truck> loadTruckbyLicensePlate(LicensePlate licensePlate) {
        return truckRepository.findByLicensePlate(licensePlate.toString())
                .map(this::mapToDomain);
    }

    @Override
    public void saveTruck(Truck truck) {
        TruckJpaEntity entity = new TruckJpaEntity();
        entity.setLicensePlate(truck.getLicensePlate().toString());
        entity.setMaterialType(truck.getMaterialType().toString());
        entity.setDockNumber(truck.getDockNumber());
        truckRepository.save(entity);
    }
}