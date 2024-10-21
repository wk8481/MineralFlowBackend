package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
import be.kdg.prgramming6.landside.port.in.*;
import be.kdg.prgramming6.landside.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GenerateWeighbridgeTicketUseCaseImpl implements GenerateWeighbridgeTicketUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GenerateWeighbridgeTicketUseCaseImpl.class);

    private final SaveWBTPort saveWBTPort;
    private final LoadWBTPort loadWBTPort;
    private final List<UpdateWBTPort> updateWBTPorts;
    private final SavePartialWBTport savePartialWBTport;
    private final LoadTruckPort loadTruckPort;
    private final ReceiveWarehouseNumberUseCase receiveWarehouseNumberUseCase;
    private final CheckArrivalTruckUseCase checkArrivalTruckUseCase;

    public GenerateWeighbridgeTicketUseCaseImpl(SaveWBTPort saveWBTPort, LoadWBTPort loadWBTPort, List<UpdateWBTPort> updateWBTPorts, SavePartialWBTport savePartialWBTport, LoadTruckPort loadTruckPort, ReceiveWarehouseNumberUseCase receiveWarehouseNumberUseCase, CheckArrivalTruckUseCase checkArrivalTruckUseCase) {
        this.saveWBTPort = saveWBTPort;
        this.loadWBTPort = loadWBTPort;
        this.updateWBTPorts = updateWBTPorts;
        this.savePartialWBTport = savePartialWBTport;
        this.loadTruckPort = loadTruckPort;
        this.receiveWarehouseNumberUseCase = receiveWarehouseNumberUseCase;
        this.checkArrivalTruckUseCase = checkArrivalTruckUseCase;
    }

    @Override
    @Transactional
    public GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
        logger.debug("Generating weighbridge ticket for license plate: {}", command.licensePlate());

        LicensePlate licensePlate = new LicensePlate(command.licensePlate());
        Truck truck = loadTruckPort.loadTruckByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Truck not found with license plate: " + command.licensePlate()));

        // Check if the truck arrived on time
//        CheckArrivalTruckCommand arrivalCommand = new CheckArrivalTruckCommand(command.licensePlate(), LocalDateTime.now());
//        CheckArrivalTruckResponse arrivalResponse = checkArrivalTruckUseCase.checkArrivalTruck(arrivalCommand);
//
//        if (!arrivalResponse.onTime()) {
//            throw new IllegalStateException("Truck did not arrive on time");
//        }

        BigDecimal tareWeight = truck.getTareWeight();
        BigDecimal netWeight = command.netWeight() != null ? command.netWeight() : command.grossWeight().subtract(tareWeight);

        logger.debug("Tare weight: {}, Net weight: {}", tareWeight, netWeight);

        Optional<WeighbridgeTicket> existingTicket = loadWBTPort.loadByLicensePlate(command.licensePlate());
        LocalDateTime timestamp = LocalDateTime.now().plusHours(1);

        WeighbridgeTicket ticket;
        WarehouseId warehouseId;
        if (existingTicket.isPresent()) {
            ticket = existingTicket.get();
            ticket.setGrossWeight(command.grossWeight());
            ticket.setNetWeight(netWeight);
            ticket.setTimestamp(timestamp);

            logger.debug("Existing ticket found. Updating ticket: {}", ticket);

            ReceiveWarehouseNumberResponse response = receiveWarehouseNumberUseCase.receiveWarehouseNumber(new ReceiveWarehouseNumberCommand(licensePlate.toString(), netWeight, truck.getMaterialType().toString(), truck.getSellerId().id()));
            warehouseId = response.getWarehouseId();

            logger.debug("Received warehouse ID: {}", warehouseId);

            updateWBTPorts.forEach(updateWBTPort -> {
                logger.debug("Updating WBT port with ticket: {} and warehouse ID: {}", ticket, warehouseId);
                updateWBTPort.update(ticket, warehouseId);
            });
        } else {
            ticket = new WeighbridgeTicket(
                    command.licensePlate(),
                    command.grossWeight(),
                    tareWeight,
                    netWeight,
                    timestamp
            );

            logger.debug("No existing ticket found. Saving new ticket: {}", ticket);
            saveWBTPort.save(ticket);
        }

        logger.info("Generated or Loaded Weighbridge Ticket: {}", ticket);

        return new GenerateWeighbridgeTicketResponse(ticket);
    }
}