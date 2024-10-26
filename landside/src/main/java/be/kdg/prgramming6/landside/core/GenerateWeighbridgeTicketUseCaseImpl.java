package be.kdg.prgramming6.landside.core;

import be.kdg.prgramming6.landside.domain.*;
import be.kdg.prgramming6.landside.port.in.*;
import be.kdg.prgramming6.landside.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final LoadTruckPort loadTruckPort;
    private final LoadWarehousePort loadWarehousePort;
    private final LoadAppointmentByLicensePlatePort loadAppointmentPort;
    private final UpdateAppointmentPort updateAppointmentPort;

    public GenerateWeighbridgeTicketUseCaseImpl(SaveWBTPort saveWBTPort, LoadWBTPort loadWBTPort, List<UpdateWBTPort> updateWBTPorts, LoadTruckPort loadTruckPort, LoadWarehousePort loadWarehousePort, LoadAppointmentByLicensePlatePort loadAppointmentPort, @Qualifier("dayScheduleDatabaseAdapter") UpdateAppointmentPort updateAppointmentPort) {
        this.saveWBTPort = saveWBTPort;
        this.loadWBTPort = loadWBTPort;
        this.updateWBTPorts = updateWBTPorts;
        this.loadTruckPort = loadTruckPort;
        this.loadWarehousePort = loadWarehousePort;
        this.loadAppointmentPort = loadAppointmentPort;
        this.updateAppointmentPort = updateAppointmentPort;
    }

    @Override
    @Transactional
    public GenerateWeighbridgeTicketResponse generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
        logger.debug("Generating weighbridge ticket for license plate: {}", command.licensePlate());

        LicensePlate licensePlate = new LicensePlate(command.licensePlate());
        Truck truck = loadTruckPort.loadTruckByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("Truck not found with license plate: " + command.licensePlate()));

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

            Warehouse warehouse = loadWarehousePort.loadWarehouse(truck.getSellerId(), truck.getMaterialType(), timestamp)
                    .orElseThrow(() -> new IllegalArgumentException("Warehouse not found for seller ID: " + truck.getSellerId().id() + " and material type: " + truck.getMaterialType()));
            warehouseId = warehouse.getWarehouseId();

            logger.debug("Loaded warehouse ID: {}", warehouseId);

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

        // Load and update the appointment
        loadAppointmentPort.loadAppointmentByLicensePlate(command.licensePlate())
                .ifPresent(appointment -> {
                    if (appointment.matches(truck)) {
                        LocalDateTime departureTime = appointment.getArrivalTime().plusMinutes(15);
                        appointment.setDepartureTime(departureTime);
                        updateAppointmentPort.updateAppointment(appointment);
                        logger.info("Updated appointment with departure time: {}", departureTime);
                    } else {
                        logger.warn("Appointment does not match the truck: {}", command.licensePlate());
                    }
                });

        logger.info("Generated or Loaded Weighbridge Ticket: {}", ticket);

        return new GenerateWeighbridgeTicketResponse(ticket);
    }
}