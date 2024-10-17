package be.kdg.prgramming6.landside.adapters.in;

import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketCommand;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketResponse;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class WeighbridgeTicketController {
    private final GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase;

    public WeighbridgeTicketController(GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase) {
        this.generateWeighbridgeTicketUseCase = generateWeighbridgeTicketUseCase;
    }

    @PostMapping("/generate-weighbridge-ticket")
    public ResponseEntity<GenerateWeighbridgeTicketResponse> generateWeighbridgeTicket(@Valid @RequestBody GenerateWeighbridgeTicketDTO dto) {
        GenerateWeighbridgeTicketCommand command = new GenerateWeighbridgeTicketCommand(
                dto.getLicensePlate(),
                dto.getGrossWeight(),
                dto.getTareWeight(),
                dto.getNetWeight()
        );
        GenerateWeighbridgeTicketResponse response = generateWeighbridgeTicketUseCase.generateWeighbridgeTicket(command);
        return ResponseEntity.ok(response);
    }
}