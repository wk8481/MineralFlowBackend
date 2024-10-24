package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import be.kdg.prgramming6.landside.port.out.LoadAppointmentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckTruckArrivalUseCaseImpl implements CheckArrivalTruckUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTruckArrivalUseCaseImpl.class);
    private final LoadAppointmentPort loadAppointmentPort;

    public CheckTruckArrivalUseCaseImpl(LoadAppointmentPort loadAppointmentPort) {
        this.loadAppointmentPort = loadAppointmentPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> loadAllAppointments() {
        List<Appointment> appointments = loadAppointmentPort.loadAllAppointments();
        appointments.forEach(app -> {
            if (app.getArrivalTime() == null) {
                app.setArrivalTime(LocalDateTime.now());
            }
            String status = app.checkArrivalStatus();
            LOGGER.info("Appointment for truck {} is {}", app.getTruck().getLicensePlate(), status);
        });
        return appointments;
    }
}