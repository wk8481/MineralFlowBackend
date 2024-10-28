package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.common.exception.NotFoundException;
import be.kdg.prgramming6.landside.domain.Warehouse;
import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.port.in.GetWarehouseByIdUseCase;
import be.kdg.prgramming6.landside.port.in.ViewTotalMaterialUseCase;
import be.kdg.prgramming6.landside.port.in.WarehouseDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class WarehouseController {
    private final ViewTotalMaterialUseCase viewTotalMaterialUseCase;
    private final GetWarehouseByIdUseCase getWarehouseByIdUseCase;

    public WarehouseController(ViewTotalMaterialUseCase viewTotalMaterialUseCase, GetWarehouseByIdUseCase getWarehouseByIdUseCase) {
        this.viewTotalMaterialUseCase = viewTotalMaterialUseCase;
        this.getWarehouseByIdUseCase = getWarehouseByIdUseCase;
    }

    @GetMapping("/total-material")
    public List<WarehouseDetailsDTO> getTotalMaterialAndDetails() {
        List<WarehouseDetailsResponse> responses = viewTotalMaterialUseCase.getTotalMaterialAndDetails();
        return responses.stream()
                .map(response -> {
                    WarehouseDetailsDTO dto = new WarehouseDetailsDTO();
                    dto.setWarehouseId(response.getWarehouseId());
                    dto.setSellerId(response.getSellerId());
                    dto.setMaterialType(response.getMaterialType());
                    dto.setCurrentCapacity(response.getCurrentCapacity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/total-material/{warehouseId}")
    public ResponseEntity<WarehouseDetailsDTO> getWarehouseById(@PathVariable UUID warehouseId) {
        try {
            Warehouse warehouse = getWarehouseByIdUseCase.getWarehouseById(new WarehouseId(warehouseId));
            WarehouseDetailsDTO dto = new WarehouseDetailsDTO();
            dto.setWarehouseId(warehouse.getWarehouseId().getValue());
            dto.setSellerId(warehouse.getSellerId().getValue());
            dto.setMaterialType(warehouse.getMaterialType().name());
            dto.setCurrentCapacity(warehouse.getCurrentCapacity());
            return ResponseEntity.ok(dto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}