package be.kdg.prgramming6.adapters.in;

import be.kdg.prgramming6.domain.LicensePlate;
import be.kdg.prgramming6.domain.MaterialType;
import be.kdg.prgramming6.domain.SellerId;
import be.kdg.prgramming6.exception.InvalidOperationException; // Importing existing exceptions
import be.kdg.prgramming6.exception.NotFoundException; // Importing existing exceptions
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class MakeAppointmentController {

    private final Logger logger = LoggerFactory.getLogger(MakeAppointmentController.class);
    private final MakeAppointmentUseCase makeAppointmentUseCase;

    public MakeAppointmentController(final MakeAppointmentUseCase makeAppointmentUseCase) {
        this.makeAppointmentUseCase = makeAppointmentUseCase;
    }

    @PostMapping("/make-appointment/{sellerId}/{licensePlate}/{materialType}/{appointmentWindowStart}/{appointmentWindowEnd}")
    public ResponseEntity<String> makeAppointment(
            @PathVariable UUID sellerId,
            @PathVariable String licensePlate,
            @PathVariable String materialType,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentWindowStart,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentWindowEnd) {

        logger.info("Received request to schedule an appointment for sellerId: {}, licensePlate: {}, materialType: {}, start: {}, end: {}",
                sellerId, licensePlate, materialType, appointmentWindowStart, appointmentWindowEnd);

        // Validate that the start time is before the end time
        if (appointmentWindowStart.isAfter(appointmentWindowEnd)) {
            logger.warn("Appointment creation failed: Start time {} is after end time {}.", appointmentWindowStart, appointmentWindowEnd);
            return ResponseEntity.badRequest().body("Start time cannot be after end time.");
        }

        // Create the command object
        MakeAppointmentCommand command = new MakeAppointmentCommand(
                new LicensePlate(licensePlate),
                MaterialType.fromString(materialType),
                appointmentWindowStart,
                appointmentWindowEnd,
                new SellerId(sellerId) // Using sellerId directly as a UUID
        );

        try {
            // Use the use case to make the appointment
            makeAppointmentUseCase.makeAppointment(command);
            logger.info("Appointment successfully scheduled for sellerId: {}", sellerId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment successfully scheduled.");
        } catch (NotFoundException e) {
            logger.error("Not found error for sellerId: {}. Error: {}", sellerId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidOperationException e) {
            logger.error("Invalid operation for sellerId: {}. Error: {}", sellerId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error scheduling appointment for sellerId: {}. Error: {}", sellerId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while scheduling the appointment.");
        }
    }
}
