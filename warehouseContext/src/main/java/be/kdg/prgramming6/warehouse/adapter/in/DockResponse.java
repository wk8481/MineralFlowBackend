package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.warehouse.domain.PayloadDeliveryTicket;
import be.kdg.prgramming6.warehouse.domain.WeighbridgeNumber;

public class DockResponse {
    private final PayloadDeliveryTicket payloadDeliveryTicket;
    private final WeighbridgeNumber weighbridgeNumber;

    public DockResponse(PayloadDeliveryTicket payloadDeliveryTicket, WeighbridgeNumber weighbridgeNumber) {
        this.payloadDeliveryTicket = payloadDeliveryTicket;
        this.weighbridgeNumber = weighbridgeNumber;
    }

    // Getters
    public PayloadDeliveryTicket getPayloadDeliveryTicket() {
        return payloadDeliveryTicket;
    }

    public WeighbridgeNumber getWeighbridgeNumber() {
        return weighbridgeNumber;
    }

    @Override
    public String toString() {
        return "DockResponse {" +
                "Payload Delivery Ticket: " + payloadDeliveryTicket +
                ", Weighbridge Number: " + weighbridgeNumber +
                '}';
    }
}
