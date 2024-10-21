package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckCommand;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckResponse;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import be.kdg.prgramming6.landside.port.out.LoadAppointmentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckTruckArrivalUseCaseImpl implements CheckArrivalTruckUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTruckArrivalUseCaseImpl.class);
    private final LoadAppointmentPort loadAppointmentPort;

    public CheckTruckArrivalUseCaseImpl(LoadAppointmentPort loadAppointmentPort) {
        this.loadAppointmentPort = loadAppointmentPort;
    }

    @Override
    @Transactional
    public CheckArrivalTruckResponse checkArrivalTruck(CheckArrivalTruckCommand command) {
        LOGGER.info("Checking arrival for license plate: {}", command.licensePlate());
        String licensePlate = command.licensePlate();
        LocalDateTime arrivalTime = command.arrivalTime();
        Optional<Appointment> appointment = loadAppointmentPort.loadAppointmentByLicensePlate(licensePlate);

        if (appointment.isEmpty()) {
            throw new IllegalStateException("No appointment found for license plate: " + licensePlate);
        }

        Appointment app = appointment.get();
        if (app.getArrivalTime() == null) {
            app.setArrivalTime(arrivalTime);
        }
        String status = app.checkArrivalStatus();

        LOGGER.info("Truck is {}", status);

        return new CheckArrivalTruckResponse("on time".equals(status), app.getSellerId().toString(), app.getMaterialType().toString());
    }
}