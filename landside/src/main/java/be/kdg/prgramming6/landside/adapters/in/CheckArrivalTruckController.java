package be.kdg.prgramming6.landside.adapters.in;

import be.kdg.prgramming6.landside.adapters.in.CheckArrivalTruckRequestDTO;
import be.kdg.prgramming6.landside.adapters.in.CheckArrivalTruckResponseDTO;
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
    public ResponseEntity<CheckArrivalTruckResponseDTO> checkArrivalTruck(@RequestBody CheckArrivalTruckRequestDTO requestDTO) {
        CheckArrivalTruckCommand command = new CheckArrivalTruckCommand(requestDTO.getLicensePlate(), requestDTO.getArrivalTime());
        CheckArrivalTruckResponse response = checkArrivalTruckUseCase.checkArrivalTruck(command);
        CheckArrivalTruckResponseDTO responseDTO = new CheckArrivalTruckResponseDTO(response.onTime());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/check-arrival")
    public ResponseEntity<CheckArrivalTruckResponseDTO> checkArrivalTruck(
            @RequestParam String licensePlate,
            @RequestParam String arrivalTime) {

        LocalDateTime parsedArrivalTime = LocalDateTime.parse(arrivalTime);
        CheckArrivalTruckCommand command = new CheckArrivalTruckCommand(licensePlate, parsedArrivalTime);
        CheckArrivalTruckResponse response = checkArrivalTruckUseCase.checkArrivalTruck(command);
        CheckArrivalTruckResponseDTO responseDTO = new CheckArrivalTruckResponseDTO(response.onTime());
        return ResponseEntity.ok(responseDTO);
    }
}