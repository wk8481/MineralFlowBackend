package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.GetTruckByLicensePlateUseCase;
import be.kdg.prgramming6.landside.port.out.LoadAppointmentByLicensePlatePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetTruckByLicensePlateUseCaseImpl implements GetTruckByLicensePlateUseCase {
    private final LoadAppointmentByLicensePlatePort loadAppointmentByLicensePlatePort;

    public GetTruckByLicensePlateUseCaseImpl(LoadAppointmentByLicensePlatePort loadAppointmentByLicensePlatePort) {
        this.loadAppointmentByLicensePlatePort = loadAppointmentByLicensePlatePort;
    }

    @Override
    @Transactional
    public Appointment getAppointmentByLicensePlate(String licensePlate) {
        return loadAppointmentByLicensePlatePort.loadAppointmentByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found for license plate: " + licensePlate));
    }
}