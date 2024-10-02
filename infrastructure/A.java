package be.kdg.programming6.adapter.in;

import be.kdg.programming6.domain.*;
import be.kdg.programming6.port.in.DockTruckCommand;
import be.kdg.programming6.port.in.DockTruckUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class DockTruckController {

    private final DockTruckUseCase dockTruckUseCase;
    private final Logger logger = LoggerFactory.getLogger(DockTruckController.class);

    public DockTruckController(DockTruckUseCase dockTruckUseCase) {
        this.dockTruckUseCase = dockTruckUseCase;
    }

    @PostMapping("/dock-truck/{licensePlate}/{materialType}/{conveyorBeltId}/{deliveryDate}/{sellerId}")
    public ResponseEntity<String> dockTruck(
            @PathVariable String licensePlate,
            @PathVariable String materialType,
            @PathVariable String conveyorBeltId,
            @PathVariable String deliveryDate,
            @PathVariable String sellerId) {

        logger.info("Received request to dock truck with license plate: {}", licensePlate);
        logger.info("Material Type: {}, Conveyor Belt ID: {}, Delivery Date: {}, Seller ID: {}",
                materialType, conveyorBeltId, deliveryDate, sellerId);

        // Convert deliveryDate string to LocalDateTime
        LocalDateTime parsedDeliveryDate;
        try {
            parsedDeliveryDate = LocalDateTime.parse(deliveryDate); // Adjust the format if necessary
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid delivery date format. Please use the correct format.");
        }

        // The rest of your existing code...
    }

        // Determine the WarehouseId
        WarehouseId warehouseId = findAvailableWarehouseId(); // Fetch or generate warehouse ID

        // If no warehouse is found, generate a static WarehouseId
        if (warehouseId == null) {
            warehouseId = new WarehouseId(UUID.fromString("00000000-0000-0000-0000-000000000001")); // Static warehouse ID as a fallback
            logger.warn("No available WarehouseId found. Using fallback WarehouseId: {}", warehouseId);
        }

        try {
            // Create the command object
            DockTruckCommand command = new DockTruckCommand(
                    new LicensePlate(licensePlate),
                    MaterialType.valueOf(materialType.toUpperCase()),
                    warehouseId,
                    conveyorBeltId,
                    deliveryDate,
                    sellerId
            );

            // Execute the docking use case
            dockTruckUseCase.dockTruck(command); // Call the method without assignment

            logger.info("Truck docked successfully: {}", command);
            // Return a success response
            return ResponseEntity.ok("Truck docked successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request parameters: {}", e.getMessage());
            // Handle specific errors related to command parameters
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while docking the truck: {}", e.getMessage(), e);
            // Handle any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while docking the truck.");
        }
    }

    // Method to find an available WarehouseId
    private WarehouseId findAvailableWarehouseId() {
        // Implement logic to fetch an available WarehouseId
        // This could involve querying your database or checking your application's state
        // Return null if no warehouse is found
        return null; // Placeholder for actual implementation
    }
}
