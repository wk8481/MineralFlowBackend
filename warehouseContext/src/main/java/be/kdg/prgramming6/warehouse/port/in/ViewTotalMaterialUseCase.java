package be.kdg.prgramming6.warehouse.port.in;

import be.kdg.prgramming6.warehouse.domain.Warehouse;
import java.util.List;

@FunctionalInterface
public interface ViewTotalMaterialUseCase {
    List<WarehouseDetailsResponse> getTotalMaterialAndDetails();
}