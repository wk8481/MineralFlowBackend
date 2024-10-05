package be.kdg.prgramming6.warehouse.port.in.out;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;

import java.util.UUID;

public interface LoadWarehousePort {
    WarehouseId findWarehouseIdBySellerId(UUID sellerId);

    WarehouseId findWarehouseIdBySellerIdAndMaterialType(UUID sellerId, MaterialType materialType);



}
