// landside/src/main/java/be/kdg/prgramming6/landside/adapters/in/web/TruckController.java
package be.kdg.prgramming6.landside.adapters.in;

import be.kdg.prgramming6.landside.adapters.in.TruckDTO;
import be.kdg.prgramming6.landside.port.in.GetAllTrucksOnSiteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class TruckController {
    private final GetAllTrucksOnSiteUseCase getAllTrucksOnSiteUseCase;

    public TruckController(GetAllTrucksOnSiteUseCase getAllTrucksOnSiteUseCase) {
        this.getAllTrucksOnSiteUseCase = getAllTrucksOnSiteUseCase;
    }

    @GetMapping("/trucks-on-site")
    public ResponseEntity<List<TruckDTO>> getAllTrucksOnSite() {
        List<TruckDTO> trucks = getAllTrucksOnSiteUseCase.getAllTrucksOnSite().stream()
                .map(truckResponse -> new TruckDTO(truckResponse.getLicensePlate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(trucks);
    }
}