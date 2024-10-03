package be.kdg.prgramming6.port.in.out;

import be.kdg.prgramming6.domain.MaterialType;
import be.kdg.prgramming6.domain.Warehouse;
import be.kdg.prgramming6.domain.WarehouseId;

import java.util.UUID;

public interface LoadWarehousePort {
    WarehouseId findWarehouseIdBySellerId(UUID sellerId);

    WarehouseId findWarehouseIdBySellerIdAndMaterialType(UUID sellerId, MaterialType materialType);



}
