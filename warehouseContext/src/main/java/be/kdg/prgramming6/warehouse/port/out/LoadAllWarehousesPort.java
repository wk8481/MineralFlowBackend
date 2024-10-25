package be.kdg.prgramming6.warehouse.port.out;

import be.kdg.prgramming6.warehouse.domain.Warehouse;

import java.util.List;

public interface LoadAllWarehousesPort {
    List<Warehouse> loadAllWarehouses();
}
