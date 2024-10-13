package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketCommand;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketResponse;
import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketUseCase;
import be.kdg.prgramming6.landside.port.out.LoadWBTPort;
import be.kdg.prgramming6.landside.port.out.SaveWBTPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GenerateWeighbridgeTicketUseCaseImpl implements GenerateWeighbridgeTicketUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GenerateWeighbridgeTicketUseCaseImpl.class);

    private final SaveWBTPort saveWBTPort;
    private final LoadWBTPort loadWBTPort;

    public GenerateWeighbridgeTicketUseCaseImpl(SaveWBTPort saveWBTPort, LoadWBTPort loadWBTPort) {
        this.saveWBTPort = saveWBTPort;
        this.loadWBTPort = loadWBTPort;
    }

    @Override
    public GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
        Optional<WeighbridgeTicket> existingTicket = loadWBTPort.loadByLicensePlate(command.licensePlate());

        WeighbridgeTicket ticket = existingTicket.orElseGet(() -> {
            WeighbridgeTicket newTicket = new WeighbridgeTicket(
                    command.licensePlate(),
                    command.grossWeight(),
                    command.tareWeight(),
                    LocalDateTime.now()
            );
            saveWBTPort.save(newTicket);
            return newTicket;
        });

        logger.info("Generated or Loaded Weighbridge Ticket: {}", ticket);
        return new GenerateWeighbridgeTicketResponse(ticket);
    }
}