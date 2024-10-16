package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.domain.Weighbridge;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketCommand;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketResponse;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketUseCase;
import be.kdg.prgramming6.landside.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class GenerateWeighbridgeTicketUseCaseImpl implements GenerateWeighbridgeTicketUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GenerateWeighbridgeTicketUseCaseImpl.class);

    private final SaveWBTPort saveWBTPort;
    private final LoadWBTPort loadWBTPort;
    private final UpdateWBTPort updateWBTPort;
    private final LoadWeighbridgePort loadWeighbridgePort;
    private final LoadTruckPort loadTruckPort;

    public GenerateWeighbridgeTicketUseCaseImpl(SaveWBTPort saveWBTPort, LoadWBTPort loadWBTPort, UpdateWBTPort updateWBTPort, LoadWeighbridgePort loadWeighbridgePort, LoadTruckPort loadTruckPort) {
        this.saveWBTPort = saveWBTPort;
        this.loadWBTPort = loadWBTPort;
        this.updateWBTPort = updateWBTPort;
        this.loadWeighbridgePort = loadWeighbridgePort;
        this.loadTruckPort = loadTruckPort;
    }



    @Override
    public GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
        // Load the weighbridge by license plate

        // Calculate net weight
        BigDecimal netWeight = command.grossWeight().subtract(command.tareWeight());

        // Generate or load the weighbridge ticket
        Optional<WeighbridgeTicket> existingTicket = loadWBTPort.loadByLicensePlate(command.licensePlate());

        LocalDateTime timestamp = LocalDateTime.now().plusHours(1);

        WeighbridgeTicket ticket = existingTicket.orElseGet(() -> {
            WeighbridgeTicket newTicket = new WeighbridgeTicket(
                    command.licensePlate(),
                    command.grossWeight(),
                    command.tareWeight(),
                    netWeight,
                    timestamp
            );
            saveWBTPort.save(newTicket);
            return newTicket;
        });

        if (existingTicket.isPresent()) {
            ticket = new WeighbridgeTicket(
                    command.licensePlate(),
                    command.grossWeight(),
                    command.tareWeight(),
                    netWeight,
                    timestamp
            );
            updateWBTPort.update(ticket);
        }

        logger.info("Generated or Loaded Weighbridge Ticket: {}", ticket);
        // Publish event with net weight
        publishEvent(ticket);

        return new GenerateWeighbridgeTicketResponse(ticket);
    }

    private void publishEvent(WeighbridgeTicket ticket) {
        // Logic to publish event with net weight
        logger.info("Publishing event with net weight: {}", ticket.getNetWeight());
    }
}