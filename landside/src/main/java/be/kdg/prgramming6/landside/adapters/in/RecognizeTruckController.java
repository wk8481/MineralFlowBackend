package be.kdg.prgramming6.landside.adapters.in;

import be.kdg.prgramming6.landside.port.in.RecognizeTruckCommand;
import be.kdg.prgramming6.landside.port.in.RecognizeTruckResponse;
import be.kdg.prgramming6.landside.port.in.RecognizeTruckUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class RecognizeTruckController {
    private final RecognizeTruckUseCase recognizeTruckUseCase;

    public RecognizeTruckController(RecognizeTruckUseCase recognizeTruckUseCase) {
        this.recognizeTruckUseCase = recognizeTruckUseCase;
    }

    @PostMapping("/recognize-truck")
    public ResponseEntity<RecognizeTruckResponse> recognizeTruck(@Valid @RequestBody RecognizeTruckDTO recognizeTruckDTO) {
        RecognizeTruckCommand command = new RecognizeTruckCommand(
                recognizeTruckDTO.getLicensePlate(),
                recognizeTruckDTO.getMaterialType(),
                recognizeTruckDTO.getDockNumber()
        );
        RecognizeTruckResponse response = recognizeTruckUseCase.recognizeTruck(command);
        return ResponseEntity.ok(response);
    }
}