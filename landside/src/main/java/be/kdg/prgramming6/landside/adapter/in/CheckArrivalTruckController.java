// landside/src/main/java/be/kdg/prgramming6/landside/adapter/in/CheckArrivalTruckController.java
package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import be.kdg.prgramming6.landside.port.in.GetTruckByIdUseCase;
import be.kdg.prgramming6.landside.port.in.GetTruckByLicensePlateUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CheckArrivalTruckController {

    private final CheckArrivalTruckUseCase checkArrivalTruckUseCase;
    private final GetTruckByLicensePlateUseCase getTruckByLicensePlateUseCase;
    private final GetTruckByIdUseCase getTruckByIdUseCase;

    public CheckArrivalTruckController(CheckArrivalTruckUseCase checkArrivalTruckUseCase, GetTruckByLicensePlateUseCase getTruckByLicensePlateUseCase, GetTruckByIdUseCase getTruckByIdUseCase) {
        this.checkArrivalTruckUseCase = checkArrivalTruckUseCase;
        this.getTruckByLicensePlateUseCase = getTruckByLicensePlateUseCase;
        this.getTruckByIdUseCase = getTruckByIdUseCase;
    }

    @GetMapping(value = "/check-arrival", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TruckOnTimeDTO>> checkArrivalTruck() {
        List<LocalDateTime> scheduleTimes = List.of(
                LocalDateTime.of(2024, 10, 30, 10, 0),
                LocalDateTime.of(2024, 11, 30, 11, 0),
                LocalDateTime.of(2024, 12, 30, 12, 0),
                LocalDateTime.of(2024, 11, 30, 10, 0),
                LocalDateTime.of(2024, 11, 30, 14, 0),
                LocalDateTime.of(2024, 11, 30, 15, 0),
                LocalDateTime.of(2024, 12, 30, 16, 0),
                LocalDateTime.of(2024, 11, 30, 17, 0),
                LocalDateTime.of(2024, 12, 30, 18, 0),
                LocalDateTime.of(2024, 11, 30, 19, 0),
                LocalDateTime.of(2024, 11, 4, 9, 0),
                LocalDateTime.now()
        );
        List<Appointment> appointments = checkArrivalTruckUseCase.loadAllAppointments(scheduleTimes);
        List<TruckOnTimeDTO> truckOnTimeDTOs = appointments.stream().map(app -> {
            String status = app.checkArrivalStatus();
            return new TruckOnTimeDTO(
                    app.getTruck().getLicensePlate().toString(),
                    app.getSellerId().toString(),
                    app.getMaterialType().toString(),
                    app.getArrivalTime(),
                    "on time".equals(status),
                    app.getWindowStart(),
                    app.getWindowEnd()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(truckOnTimeDTOs);
    }

    @GetMapping(value = "/check-arrival/license-plate/{licensePlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TruckOnTimeDTO> getAppointmentByLicensePlate(@PathVariable String licensePlate) {
        Appointment appointment = getTruckByLicensePlateUseCase.getAppointmentByLicensePlate(licensePlate);
        TruckOnTimeDTO dto = new TruckOnTimeDTO(
                appointment.getTruck().getLicensePlate().toString(),
                appointment.getSellerId().toString(),
                appointment.getMaterialType().toString(),
                appointment.getArrivalTime(),
                "on time".equals(appointment.checkArrivalStatus()),
                appointment.getWindowStart(),
                appointment.getWindowEnd()
        );
        return ResponseEntity.ok(dto);
    }


    @GetMapping(value = "/check-arrival/id/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TruckOnTimeDTO> getAppointmentById(@PathVariable int appointmentId) {
        Appointment appointment = getTruckByIdUseCase.getAppointmentById(appointmentId);
        TruckOnTimeDTO dto = new TruckOnTimeDTO(
                appointment.getTruck().getLicensePlate().toString(),
                appointment.getSellerId().toString(),
                appointment.getMaterialType().toString(),
                appointment.getArrivalTime(),
                "on time".equals(appointment.checkArrivalStatus()),
                appointment.getWindowStart(),
                appointment.getWindowEnd()
        );
        return ResponseEntity.ok(dto);
    }
}
