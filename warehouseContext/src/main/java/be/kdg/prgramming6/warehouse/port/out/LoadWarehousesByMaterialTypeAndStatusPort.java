package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.WarehouseActivity;

import java.util.List;

public interface LoadWarehousesByMaterialTypeAndStatusPort {
    List<WarehouseActivity> loadAllDeliveriesByMaterialTypeAndStatus(MaterialType materialType, String status);
}