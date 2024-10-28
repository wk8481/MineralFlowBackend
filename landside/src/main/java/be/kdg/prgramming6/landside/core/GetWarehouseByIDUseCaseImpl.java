package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.port.in.GetWarehouseByIdUseCase;
import be.kdg.prgramming6.landside.port.out.LoadWarehouseByIDPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GetWarehouseByIDUseCaseImpl implements GetWarehouseByIdUseCase {
    private final LoadWarehouseByIDPort loadWarehouseByIDPort;

    public GetWarehouseByIDUseCaseImpl(LoadWarehouseByIDPort loadWarehouseByIDPort) {
        this.loadWarehouseByIDPort = loadWarehouseByIDPort;
    }

    @Override
    @Transactional
    public Warehouse getWarehouseById(WarehouseId warehouseId) {
        return loadWarehouseByIDPort.loadWarehouseByID(warehouseId);
    }
}
