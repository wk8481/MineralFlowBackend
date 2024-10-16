package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.warehouse.domain.LicensePlate;
import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.WarehouseId;
import be.kdg.prgramming6.warehouse.port.in.DockTruckCommand;
import be.kdg.prgramming6.warehouse.port.in.DockTruckUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DockTruckController {

    private final DockTruckUseCase dockTruckUseCase;
    private final Logger logger = LoggerFactory.getLogger(DockTruckController.class);

    public DockTruckController(DockTruckUseCase dockTruckUseCase) {
        this.dockTruckUseCase = dockTruckUseCase;
    }

    @PostMapping("/dock-truck")
    public ResponseEntity<String> dockTruck(@Valid @RequestBody DockTruckDTO dockTruckDTO) {
        try {
            // Create the command object with required parameters
            DockTruckCommand command = new DockTruckCommand(
                    new LicensePlate(dockTruckDTO.getLicensePlate()),
                    MaterialType.valueOf(dockTruckDTO.getMaterialType().toUpperCase()), // Convert string to MaterialType
                    new WarehouseId(dockTruckDTO.getWarehouseId()), // Use the warehouseId from the DTO
                    dockTruckDTO.getDockNumber(),
                    dockTruckDTO.getDeliveryDate(),
                    dockTruckDTO.getSellerId()
                    // sellerId can be added later when available
            );

            // Execute the docking use case
            dockTruckUseCase.dockTruck(command); // Call the docking method

            // Log the success
            logger.info("Truck with license plate {} docked successfully in warehouse {}.",
                    dockTruckDTO.getLicensePlate(), dockTruckDTO.getWarehouseId());
            return ResponseEntity.ok("Truck docked successfully");
        } catch (IllegalArgumentException e) {
            // Handle specific errors related to command parameters
            logger.error("Error docking truck: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected exceptions
            logger.error("An unexpected error occurred while docking the truck.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while docking the truck.");
        }
    }
}