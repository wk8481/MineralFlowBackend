package be.kdg.programming6.port.in.out;

import be.kdg.programming6.domain.WarehouseId;

import java.util.UUID;

public interface LoadWarehousePort {
    WarehouseId findWarehouseIdbySellerId(UUID sellerId);
}
