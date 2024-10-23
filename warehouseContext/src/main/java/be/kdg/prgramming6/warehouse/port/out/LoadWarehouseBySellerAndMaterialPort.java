package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;

import java.util.UUID;

@FunctionalInterface
public interface LoadWarehouseBySellerAndMaterialPort {
    WarehouseId findWarehouseIdBySellerIdAndMaterialType(UUID sellerId, MaterialType materialType);




}
