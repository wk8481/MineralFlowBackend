package be.kdg.prgramming6.api;

import be.kdg.prgramming6.domain.LicensePlate;
import be.kdg.prgramming6.domain.MaterialType;
import be.kdg.prgramming6.port.in.MakeAppointmentCommand;
import be.kdg.prgramming6.port.in.MakeAppointmentUseCase;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/appointments")
public class MakeAppointmentController {

    private final MakeAppointmentUseCase makeAppointmentUseCase;

    public MakeAppointmentController(final MakeAppointmentUseCase makeAppointmentUseCase) {
        this.makeAppointmentUseCase = makeAppointmentUseCase;
    }

    @PostMapping
    public void makeAppointment(
            @RequestParam("licensePlate") final String licensePlate,
            @RequestParam("materialType") final String materialType,
            @RequestParam("windowStart") final LocalDateTime appointmentWindowStart,
            @RequestParam("windowEnd") final LocalDateTime appointmentWindowEnd) {

        MakeAppointmentCommand command = new MakeAppointmentCommand(
                new LicensePlate(licensePlate),
                MaterialType.fromString(materialType), // Use the static method to get the enum value
                appointmentWindowStart,
                appointmentWindowEnd
        );

        makeAppointmentUseCase.makeAppointment(command);
    }
}
