package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.domain.LicensePlate;
import be.kdg.prgramming6.landside.domain.MaterialType;
import be.kdg.prgramming6.landside.domain.SellerId;
import be.kdg.prgramming6.common.exception.InvalidOperationException;
import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.landside.port.in.MakeAppointmentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
@Validated
public class MakeAppointmentController {

    private final Logger logger = LoggerFactory.getLogger(MakeAppointmentController.class);
    private final MakeAppointmentUseCase makeAppointmentUseCase;

    public MakeAppointmentController(final MakeAppointmentUseCase makeAppointmentUseCase) {
        this.makeAppointmentUseCase = makeAppointmentUseCase;
    }

    @PostMapping("/make-appointment")
    public ResponseEntity<Object> makeAppointment(@Valid @RequestBody MakeAppointmentRequestDTO request) {
        logger.info("Received request to schedule an appointment: {}", request);

        // Validate the DTO, which includes time window validation and material type validation
        try {
            request.validate();
        } catch (InvalidOperationException e) {
            logger.warn("Appointment creation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // Create the command object
        MakeAppointmentCommand command = new MakeAppointmentCommand(
                new LicensePlate(request.getLicensePlate()),
                MaterialType.fromString(request.getMaterialType()), // This will not throw an exception now
                request.getAppointmentWindowStart(),
                request.getAppointmentWindowEnd(),
                new SellerId(request.getSellerId())
        );

        try {
            // Use the use case to make the appointment
            makeAppointmentUseCase.makeAppointment(command);
            logger.info("Appointment successfully scheduled for sellerId: {}", request.getSellerId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment successfully scheduled.");
        } catch (NotFoundException e) {
            logger.error("Not found error for sellerId: {}. Error: {}", request.getSellerId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidOperationException e) {
            logger.error("Invalid operation for sellerId: {}. Error: {}", request.getSellerId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error scheduling appointment for sellerId: {}. Error: {}", request.getSellerId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while scheduling the appointment.");
        }
    }
}
