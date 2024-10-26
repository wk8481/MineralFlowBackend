package be.kdg.prgramming6.landside.core;


import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.port.in.ViewTotalMaterialUseCase;
import be.kdg.prgramming6.landside.port.in.WarehouseDetailsResponse;
import be.kdg.prgramming6.landside.port.out.LoadAllWarehousesPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewTotalMaterialUseCaseImpl implements ViewTotalMaterialUseCase {
    private final LoadAllWarehousesPort loadAllWarehousesPort;

    public ViewTotalMaterialUseCaseImpl(LoadAllWarehousesPort loadAllWarehousesPort) {
        this.loadAllWarehousesPort = loadAllWarehousesPort;
    }

    @Override
    public List<WarehouseDetailsResponse> getTotalMaterialAndDetails() {
        List<Warehouse> warehouses = loadAllWarehousesPort.loadAllWarehouses();
        return warehouses.stream()
                .map(warehouse -> new WarehouseDetailsResponse(
                        warehouse.getWarehouseId().id(),
                        warehouse.getSellerId().id(),
                        warehouse.getMaterialType().toString(),
                        warehouse.getCurrentCapacity() // Use the getTotalMaterial method
                ))
                .collect(Collectors.toList());
    }
}