// landside/src/main/java/be/kdg/prgramming6/landside/adapter/in/TruckOnSiteController.java
package be.kdg.prgramming6.landside.adapter.in;

import be.kdg.prgramming6.landside.port.in.GetAllTrucksOnSiteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class TruckOnSiteController {
    private final GetAllTrucksOnSiteUseCase getAllTrucksOnSiteUseCase;

    public TruckOnSiteController(GetAllTrucksOnSiteUseCase getAllTrucksOnSiteUseCase) {
        this.getAllTrucksOnSiteUseCase = getAllTrucksOnSiteUseCase;
    }

    @GetMapping("/trucks-on-site")
    public ResponseEntity<List<TruckDTO>> getAllTrucksOnSite() {
        List<LocalDateTime> scheduleTimes = List.of(
                LocalDateTime.of(2024, 10, 30, 10, 0),
                LocalDateTime.of(2024, 11, 30, 11, 0),
                LocalDateTime.of(2024, 12, 30, 12, 0),
                LocalDateTime.of(2024, 11, 30, 10, 0),
                LocalDateTime.of(2024, 11, 30, 14, 0),
                LocalDateTime.of(2024, 11, 30, 15, 0),
                LocalDateTime.of(2024, 12, 30, 16, 0),
                LocalDateTime.of(2024, 11, 30, 17, 0),
                LocalDateTime.of(2024, 12, 30, 18, 0),
                LocalDateTime.of(2024, 11, 30, 19, 0),
                LocalDateTime.now()
        );
        List<TruckDTO> trucks = getAllTrucksOnSiteUseCase.getAllTrucksOnSite(scheduleTimes).stream()
                .map(truckResponse -> new TruckDTO(truckResponse.getLicensePlate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(trucks);
    }
}