// landside/src/main/java/be/kdg/prgramming6/landside/core/RecognizeTruckUseCaseImpl.java
package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.in.RecognizeTruckCommand;
import be.kdg.prgramming6.landside.port.in.RecognizeTruckResponse;
import be.kdg.prgramming6.landside.port.in.RecognizeTruckUseCase;
import be.kdg.prgramming6.landside.port.out.LoadTruckPort;
import be.kdg.prgramming6.landside.port.out.SaveTruckPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RecognizeTruckUseCaseImpl implements RecognizeTruckUseCase {
    private final LoadTruckPort loadTruckPort;
    private final SaveTruckPort saveTruckPort;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecognizeTruckUseCaseImpl.class);

    public RecognizeTruckUseCaseImpl(LoadTruckPort loadTruckPort, SaveTruckPort saveTruckPort) {
        this.loadTruckPort = loadTruckPort;
        this.saveTruckPort = saveTruckPort;
    }

    @Override
    @Transactional
    public RecognizeTruckResponse recognizeTruck(RecognizeTruckCommand command) {
        LicensePlate licensePlate = new LicensePlate(command.licensePlate());
        MaterialType materialType = MaterialType.valueOf(command.materialType());
        String dockNumber = command.dockNumber();

        Truck truck = loadTruckPort.loadTruckByLicensePlate(licensePlate)
                .orElseGet(() -> {
                    Truck newTruck = new Truck(licensePlate, materialType, dockNumber);
                    saveTruckPort.saveTruck(newTruck);
                    return newTruck;
                });

        boolean gateOpened = truck.isValidLicensePlate() && truck.canDock();
        if (gateOpened) {
            LOGGER.info("Truck recognized: {}", truck.getLicensePlate());
            // Logic to open the gate
        } else {
            LOGGER.warn("Truck not recognized: {}", command.licensePlate());
        }

        return new RecognizeTruckResponse(gateOpened);
    }
}