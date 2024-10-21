package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckCommand;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckResponse;
import be.kdg.prgramming6.landside.port.in.CheckArrivalTruckUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class CheckArrivalTruckController {

    private final CheckArrivalTruckUseCase checkArrivalTruckUseCase;

    public CheckArrivalTruckController(CheckArrivalTruckUseCase checkArrivalTruckUseCase) {
        this.checkArrivalTruckUseCase = checkArrivalTruckUseCase;
    }

    @PostMapping("/check-arrival")
    public ResponseEntity<TruckOnTimeDTO> checkArrivalTruck(@RequestBody CheckArrivalTruckRequestDTO requestDTO) {
        CheckArrivalTruckCommand command = new CheckArrivalTruckCommand(requestDTO.getLicensePlate(), requestDTO.getArrivalTime());
        CheckArrivalTruckResponse response = checkArrivalTruckUseCase.checkArrivalTruck(command);
        TruckOnTimeDTO truckOnTimeDTO = new TruckOnTimeDTO(
                requestDTO.getLicensePlate(),
                response.getSellerId(),
                response.getMaterialType(),
                requestDTO.getArrivalTime(),
                response.isOnTime()
        );
        return ResponseEntity.ok(truckOnTimeDTO);
    }

    @GetMapping("/check-arrival")
    public ResponseEntity<TruckOnTimeDTO> checkArrivalTruck(
            @RequestParam String licensePlate,
            @RequestParam String arrivalTime) {

        LocalDateTime parsedArrivalTime = LocalDateTime.parse(arrivalTime);
        CheckArrivalTruckCommand command = new CheckArrivalTruckCommand(licensePlate, parsedArrivalTime);
        CheckArrivalTruckResponse response = checkArrivalTruckUseCase.checkArrivalTruck(command);
        TruckOnTimeDTO truckOnTimeDTO = new TruckOnTimeDTO(
                licensePlate,
                response.getSellerId(),
                response.getMaterialType(),
                parsedArrivalTime,
                response.isOnTime()
        );
        return ResponseEntity.ok(truckOnTimeDTO);
    }
}