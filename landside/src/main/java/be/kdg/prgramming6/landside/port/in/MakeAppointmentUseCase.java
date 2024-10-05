package be.kdg.prgramming6.landside.port.in;

@FunctionalInterface
public interface MakeAppointmentUseCase {
    void makeAppointment(MakeAppointmentCommand command);
}
