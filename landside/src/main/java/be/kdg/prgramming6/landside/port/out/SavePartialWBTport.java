// landside/src/main/java/be/kdg/prgramming6/landside/port/out/SavePartialPDTport.java
package be.kdg.prgramming6.landside.port.out;

import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;

@FunctionalInterface
public interface SavePartialWBTport {
    void savePartial(WeighbridgeTicket ticket);
}