package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberCommand;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberResponse;
import be.kdg.prgramming6.landside.port.in.ReceiveWarehouseNumberUseCase;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class ReceiveWarehouseNumberController {
    private final ReceiveWarehouseNumberUseCase receiveWarehouseNumberUseCase;

    public ReceiveWarehouseNumberController(ReceiveWarehouseNumberUseCase receiveWarehouseNumberUseCase) {
        this.receiveWarehouseNumberUseCase = receiveWarehouseNumberUseCase;
    }

    @PostMapping("/receive-warehouse-number")
    public ReceiveWarehouseNumberResponse receiveWarehouseNumber(@RequestBody @Valid ReceiveWarehouseNumberDTO dto) {
        ReceiveWarehouseNumberCommand command = new ReceiveWarehouseNumberCommand(
                dto.getLicensePlate(),
                dto.getNetWeight(),
                dto.getMaterialType(),
                dto.getSellerId()
        );
        return receiveWarehouseNumberUseCase.receiveWarehouseNumber(command);
    }
}