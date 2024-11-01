package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.GetTruckByIdUseCase;
import be.kdg.prgramming6.landside.port.out.LoadAppointmentByIdPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetTruckByIdUseCaseImpl implements GetTruckByIdUseCase {
    private final LoadAppointmentByIdPort loadAppointmentByIdPort;

    public GetTruckByIdUseCaseImpl(LoadAppointmentByIdPort loadAppointmentByIdPort) {
        this.loadAppointmentByIdPort = loadAppointmentByIdPort;
    }

    @Override
    @Transactional
    public Appointment getAppointmentById(int appointmentId) {
        return loadAppointmentByIdPort.loadAppointmentById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found for ID: " + appointmentId));
    }
}