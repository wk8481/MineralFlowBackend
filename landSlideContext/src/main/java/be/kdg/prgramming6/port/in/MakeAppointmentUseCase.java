package be.kdg.prgramming6.port.in;

@FunctionalInterface
public interface MakeAppointmentUseCase {
    void makeAppointment(MakeAppointmentCommand command);
}
