package be.kdg.prgramming6.landside.port.out;


import be.kdg.prgramming6.landside.domain.Warehouse;

import java.util.List;

@FunctionalInterface
public interface LoadAllWarehousesPort {
    List<Warehouse> loadAllWarehouses();
}
