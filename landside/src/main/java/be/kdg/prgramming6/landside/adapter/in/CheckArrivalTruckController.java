package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.domain.Appointment;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CheckArrivalTruckController {

    private final CheckArrivalTruckUseCase checkArrivalTruckUseCase;

    public CheckArrivalTruckController(CheckArrivalTruckUseCase checkArrivalTruckUseCase) {
        this.checkArrivalTruckUseCase = checkArrivalTruckUseCase;
    }

    @GetMapping("/check-arrival")
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
}