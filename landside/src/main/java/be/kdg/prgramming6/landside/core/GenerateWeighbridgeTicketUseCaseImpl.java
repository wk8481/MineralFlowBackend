//package be.kdg.prgramming6.landside.core;
//
//import be.kdg.prgramming6.landside.domain.Truck;
//import be.kdg.prgramming6.landside.domain.Weighbridge;
//import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
//import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketCommand;
//import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketResponse;
//import be.kdg.prgramming6.landside.port.in.GenerateWeighbridgeTicketUseCase;
//import be.kdg.prgramming6.landside.port.out.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class GenerateWeighbridgeTicketUseCaseImpl implements GenerateWeighbridgeTicketUseCase {
//
//    private static final Logger logger = LoggerFactory.getLogger(GenerateWeighbridgeTicketUseCaseImpl.class);
//
//    private final SaveWBTPort saveWBTPort;
//    private final LoadWBTPort loadWBTPort;
//    private final UpdateWBTPort updateWBTPort;
//    private final LoadWeighbridgePort loadWeighbridgePort;
//    private final LoadTruckPort loadTruckPort;
//
//    public GenerateWeighbridgeTicketUseCaseImpl(SaveWBTPort saveWBTPort, LoadWBTPort loadWBTPort, UpdateWBTPort updateWBTPort, LoadWeighbridgePort loadWeighbridgePort, LoadTruckPort loadTruckPort) {
//        this.saveWBTPort = saveWBTPort;
//        this.loadWBTPort = loadWBTPort;
//        this.updateWBTPort = updateWBTPort;
//        this.loadWeighbridgePort = loadWeighbridgePort;
//        this.loadTruckPort = loadTruckPort;
//    }
//
//    @Override
//    public GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
//        // Load the weighbridge by license plate
//        Weighbridge weighbridge = loadWeighbridgePort.loadWeighbridgeByLicensePlate(command.licensePlate())
//                .orElseThrow(() -> new IllegalArgumentException("Weighbridge not found for license plate: " + command.licensePlate()));
//
//        // Load the truck by license plate
//        Truck truck = loadTruckPort.loadTruckByLicensePlate(command.licensePlate())
//                .orElseThrow(() -> new IllegalArgumentException("Truck not found for license plate: " + command.licensePlate()));
//
//        // Generate or load the weighbridge ticket
//        Optional<WeighbridgeTicket> existingTicket = loadWBTPort.loadByLicensePlate(command.licensePlate());
//
//        WeighbridgeTicket ticket = existingTicket.orElseGet(() -> {
//            WeighbridgeTicket newTicket = new WeighbridgeTicket(
//                    command.licensePlate(),
//                    command.grossWeight(),
//                    command.tareWeight(),
//                    LocalDateTime.now()
//            );
//            saveWBTPort.save(newTicket);
//            return newTicket;
//        });
//
//        if (existingTicket.isPresent()) {
//            ticket = new WeighbridgeTicket(
//                    command.licensePlate(),
//                    command.grossWeight(),
//                    command.tareWeight(),
//                    LocalDateTime.now()
//            );
//            updateWBTPort.update(ticket);
//        }
//
//        logger.info("Generated or Loaded Weighbridge Ticket: {}", ticket);
//        return new GenerateWeighbridgeTicketResponse(ticket);
//    }
//}