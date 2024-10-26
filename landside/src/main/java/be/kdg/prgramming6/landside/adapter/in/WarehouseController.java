// warehouseContext/src/main/java/be/kdg/prgramming6/warehouse/adapter/in/web/WarehouseController.java
package be.kdg.prgramming6.landside.adapter.in;



import be.kdg.prgramming6.landside.port.in.ViewTotalMaterialUseCase;
import be.kdg.prgramming6.landside.port.in.WarehouseDetailsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class WarehouseController {
    private final ViewTotalMaterialUseCase viewTotalMaterialUseCase;

    public WarehouseController(ViewTotalMaterialUseCase viewTotalMaterialUseCase) {
        this.viewTotalMaterialUseCase = viewTotalMaterialUseCase;
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
}