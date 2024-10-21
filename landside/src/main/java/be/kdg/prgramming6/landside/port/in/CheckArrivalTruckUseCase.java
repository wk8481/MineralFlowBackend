package be.kdg.prgramming6.landside.port.in;

@FunctionalInterface
public interface CheckArrivalTruckUseCase {
    CheckArrivalTruckResponse checkArrivalTruck(CheckArrivalTruckCommand command);
}
