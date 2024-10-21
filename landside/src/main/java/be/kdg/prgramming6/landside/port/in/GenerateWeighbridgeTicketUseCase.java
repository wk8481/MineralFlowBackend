package be.kdg.prgramming6.landside.port.in;


@FunctionalInterface
public interface GenerateWeighbridgeTicketUseCase {
    GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command);
}