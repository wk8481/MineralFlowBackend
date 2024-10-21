package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketCommand;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketResponse;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class WeighbridgeTicketController {
    private final GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase;
    private final static Logger LOGGER = LoggerFactory.getLogger(WeighbridgeTicketController.class.getName());

    public WeighbridgeTicketController(GenerateWeighbridgeTicketUseCase generateWeighbridgeTicketUseCase) {
        this.generateWeighbridgeTicketUseCase = generateWeighbridgeTicketUseCase;
    }

    @PostMapping("/generate-weighbridge-ticket")
    public ResponseEntity<GenerateWeighbridgeTicketResponse> generateWeighbridgeTicket(@Valid @RequestBody GenerateWeighbridgeTicketDTO dto) {
        LOGGER.info("Received request to generate weighbridge ticket");
        LOGGER.debug("Request details - License Plate: {}, Gross Weight: {}, Tare Weight: {}, Net Weight: {}",
                dto.getLicensePlate(), dto.getGrossWeight(), dto.getTareWeight(), dto.getNetWeight());

        GenerateWeighbridgeTicketCommand command = new GenerateWeighbridgeTicketCommand(
                dto.getLicensePlate(),
                dto.getGrossWeight(),
                dto.getTareWeight(),
                dto.getNetWeight()
        );

        LOGGER.debug("Generated command: {}", command);

        GenerateWeighbridgeTicketResponse response = generateWeighbridgeTicketUseCase.generateWeighbridgeTicket(command);

        LOGGER.info("Weighbridge ticket generated successfully for license plate: {}", dto.getLicensePlate());
        LOGGER.debug("Response details: {}", response);

        return ResponseEntity.ok(response);
    }
}