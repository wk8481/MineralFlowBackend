package be.kdg.prgramming6.landside.port.in;

public class RecognizeTruckResponse {
    private final boolean gateOpened;

    public RecognizeTruckResponse(boolean gateOpened) {
        this.gateOpened = gateOpened;
    }

    public boolean isGateOpened() {
        return gateOpened;
    }
}