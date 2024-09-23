package be.kdg.prgramming6.adapters.in;

import be.kdg.prgramming6.domain.LicensePlate;
import be.kdg.prgramming6.domain.MaterialType;
import be.kdg.prgramming6.domain.SellerId;
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class MakeAppointmentController {

    private final Logger logger = LoggerFactory.getLogger(MakeAppointmentController.class);

    private final MakeAppointmentUseCase makeAppointmentUseCase;

    public MakeAppointmentController(final MakeAppointmentUseCase makeAppointmentUseCase) {
        this.makeAppointmentUseCase = makeAppointmentUseCase;
    }

    @PostMapping
    public void makeAppointment(@RequestBody MakeAppointmentDTO dto) {
        logger.info("Received request to schedule an appointment.");

        // Creating the command object using data from the DTO
        MakeAppointmentCommand command = new MakeAppointmentCommand(
                new LicensePlate(dto.licensePlate()),
                MaterialType.fromString(dto.materialType()),
                dto.appointmentWindowStart(),
                dto.appointmentWindowEnd(),
                new SellerId(dto.sellerId()) // Converting sellerId to SellerId
        );

        // Use the use case to make the appointment
        makeAppointmentUseCase.makeAppointment(command);

        logger.info("Appointment successfully scheduled.");
    }
}
