package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.Truck;
import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
import be.kdg.prgramming6.landside.port.in.*;
import be.kdg.prgramming6.landside.port.out.*;
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
    private final ReceiveWarehouseNumberUseCaseImpl receiveWarehouseNumberUseCaseImpl;

    public GenerateWeighbridgeTicketUseCaseImpl(SaveWBTPort saveWBTPort, LoadWBTPort loadWBTPort, List<UpdateWBTPort> updateWBTPorts, SavePartialWBTport savePartialWBTport, LoadTruckPort loadTruckPort, ReceiveWarehouseNumberUseCaseImpl receiveWarehouseNumberUseCaseImpl) {
        this.saveWBTPort = saveWBTPort;
        this.loadWBTPort = loadWBTPort;
        this.updateWBTPorts = updateWBTPorts;
        this.savePartialWBTport = savePartialWBTport;
        this.loadTruckPort = loadTruckPort;
        this.receiveWarehouseNumberUseCaseImpl = receiveWarehouseNumberUseCaseImpl;
    }

    @Override
    public GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
        LicensePlate licensePlate = new LicensePlate(command.licensePlate());
        Truck truck = loadTruckPort.loadTruckByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Truck not found with license plate: " + command.licensePlate()));

        BigDecimal tareWeight = truck.getTareWeight();
        BigDecimal netWeight = command.netWeight() != null ? command.netWeight() : command.grossWeight().subtract(tareWeight);

        Optional<WeighbridgeTicket> existingTicket = loadWBTPort.loadByLicensePlate(command.licensePlate());
        LocalDateTime timestamp = LocalDateTime.now().plusHours(1);

        WeighbridgeTicket ticket;
        WarehouseId warehouseId;
        if (existingTicket.isPresent()) {
            WeighbridgeTicket oldTicket = existingTicket.get();
            ticket = new WeighbridgeTicket(
                    oldTicket.getLicensePlate(),
                    command.grossWeight(),
                    tareWeight,
                    netWeight,
                    timestamp
            );

            ReceiveWarehouseNumberResponse response = receiveWarehouseNumberUseCaseImpl.receiveWarehouseNumber(new ReceiveWarehouseNumberCommand(licensePlate.toString(), netWeight, truck.getMaterialType().toString(), truck.getSellerId().id() ));
            warehouseId = response.getWarehouseId();

            updateWBTPorts.forEach(updateWBTPort -> updateWBTPort.update(ticket, warehouseId));
        } else {
            ticket = new WeighbridgeTicket(
                    command.licensePlate(),
                    command.grossWeight(),
                    tareWeight,
                    netWeight,
                    timestamp
            );
            saveWBTPort.save(ticket);
        }

        logger.info("Generated or Loaded Weighbridge Ticket: {}", ticket);

        return new GenerateWeighbridgeTicketResponse(ticket);
    }
}