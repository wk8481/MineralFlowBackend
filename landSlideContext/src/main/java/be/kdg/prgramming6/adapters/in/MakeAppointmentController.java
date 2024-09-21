package be.kdg.prgramming6.adapters.in;

import be.kdg.prgramming6.domain.LicensePlate;
import be.kdg.prgramming6.domain.MaterialType;
import be.kdg.prgramming6.domain.PersonId; // Import PersonId
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class MakeAppointmentController {

    private final MakeAppointmentUseCase makeAppointmentUseCase;

    public MakeAppointmentController(final MakeAppointmentUseCase makeAppointmentUseCase) {
        this.makeAppointmentUseCase = makeAppointmentUseCase;
    }

    @GetMapping
    public void makeAppointment(
            @RequestParam("licensePlate") final String licensePlate,
            @RequestParam("materialType") final String materialType,
            @RequestParam("windowStart") final LocalDateTime appointmentWindowStart,
            @RequestParam("windowEnd") final LocalDateTime appointmentWindowEnd,
            @RequestParam("seller") final UUID sellerId) { // Changed to UUID

        MakeAppointmentCommand command = new MakeAppointmentCommand(
                new LicensePlate(licensePlate),
                MaterialType.fromString(materialType),
                appointmentWindowStart,
                appointmentWindowEnd,
                new PersonId(sellerId) // Use sellerId to create PersonId
        );

        makeAppointmentUseCase.makeAppointment(command);
    }
}
