package be.kdg.prgramming6.landside.adapters.in;

import be.kdg.prgramming6.landside.adapters.in.GenerateWeighbridgeTicketDTO;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketCommand;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketResponse;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/weighbridge-tickets")
public class WeighbridgeTicketController {

    private final GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase;

    @Autowired
    public WeighbridgeTicketController(GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase) {
        this.generateWeighbridgeTicketUseCase = generateWeighbridgeTicketUseCase;
    }

    @PostMapping
    public ResponseEntity<GenerateWeighbridgeTicketResponse> generateWeighbridgeTicket(@Valid @RequestBody GenerateWeighbridgeTicketDTO dto) {
        GenerateWeighbridgeTicketCommand command = new GenerateWeighbridgeTicketCommand(
                dto.getLicensePlate(),
                dto.getGrossWeight(),
                dto.getTareWeight()
        );
        GenerateWeighbridgeTicketResponse response = generateWeighbridgeTicketUseCase.generateWeighbridgeTicket(command);
        return ResponseEntity.ok(response);

    }
}