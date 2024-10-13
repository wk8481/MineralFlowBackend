package be.kdg.prgramming6.landside.port.in;

import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;

public class GenerateWeighbridgeTicketResponse {
    private final WeighbridgeTicket weighbridgeTicket;

    public GenerateWeighbridgeTicketResponse(WeighbridgeTicket weighbridgeTicket) {
        this.weighbridgeTicket = weighbridgeTicket;
    }

    public WeighbridgeTicket getWeighbridgeTicket() {
        return weighbridgeTicket;
    }
}