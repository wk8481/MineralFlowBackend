package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import be.kdg.prgramming6.landside.port.out.LoadTrucksByDaySchedulePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CheckTruckArrivalUseCaseImpl implements CheckArrivalTruckUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTruckArrivalUseCaseImpl.class);
    private final LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort;

    public CheckTruckArrivalUseCaseImpl(LoadTrucksByDaySchedulePort loadTrucksByDaySchedulePort) {
        this.loadTrucksByDaySchedulePort = loadTrucksByDaySchedulePort;
    }

    @Override
    @Transactional
    public List<Appointment> loadAllAppointments(List<LocalDateTime> scheduleTimes) {
        List<Appointment> appointments = loadTrucksByDaySchedulePort.loadTrucksByDaySchedules(scheduleTimes);
        if (appointments.isEmpty()) {
            throw new NotFoundException("No appointments found");
        }
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