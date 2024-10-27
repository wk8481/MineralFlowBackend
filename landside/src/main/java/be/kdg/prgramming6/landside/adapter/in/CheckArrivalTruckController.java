package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import be.kdg.prgramming6.landside.port.in.GetTruckByLicensePlateUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CheckArrivalTruckController {

    private final CheckArrivalTruckUseCase checkArrivalTruckUseCase;
    private final GetTruckByLicensePlateUseCase getTruckByLicensePlateUseCase;

    public CheckArrivalTruckController(CheckArrivalTruckUseCase checkArrivalTruckUseCase, GetTruckByLicensePlateUseCase getTruckByLicensePlateUseCase) {
        this.checkArrivalTruckUseCase = checkArrivalTruckUseCase;
        this.getTruckByLicensePlateUseCase = getTruckByLicensePlateUseCase;
    }

    @GetMapping(value = "/check-arrival", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TruckOnTimeDTO>> checkArrivalTruck() {
        List<Appointment> appointments = checkArrivalTruckUseCase.loadAllAppointments();
        List<TruckOnTimeDTO> truckOnTimeDTOs = appointments.stream().map(app -> {
            String status = app.checkArrivalStatus();
            return new TruckOnTimeDTO(
                    app.getTruck().getLicensePlate().toString(),
                    app.getSellerId().toString(),
                    app.getMaterialType().toString(),
                    app.getArrivalTime(),
                    "on time".equals(status)
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(truckOnTimeDTOs);
    }

    @GetMapping(value = "/check-arrival/{licensePlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TruckOnTimeDTO> getAppointmentByLicensePlate(@PathVariable String licensePlate) {
        Appointment appointment = getTruckByLicensePlateUseCase.getAppointmentByLicensePlate(licensePlate);
        TruckOnTimeDTO dto = new TruckOnTimeDTO(
                appointment.getTruck().getLicensePlate().toString(),
                appointment.getSellerId().toString(),
                appointment.getMaterialType().toString(),
                appointment.getArrivalTime(),
                "on time".equals(appointment.checkArrivalStatus())
        );
        return ResponseEntity.ok(dto);
    }
}