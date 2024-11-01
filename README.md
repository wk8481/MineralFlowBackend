Here's the updated **README** file draft incorporating your commands and events:

---

# README - Landside and Warehouse Domain

This project is designed to support `Landside` and `Warehouse` management functionalities. Each domain includes specific commands, events, and entity definitions to ensure seamless operations, from truck appointment scheduling to inventory handling and order fulfillment.

## Table of Contents
1. [Landside Domain](#landside-domain)
2. [Warehouse Domain](#warehouse-domain)
3. [Commands and Events](#commands-and-events)
4. [Domain Entities](#domain-entities)

---

### Landside Domain
The `Landside` domain handles truck scheduling and management related to facility entry and deliveries.

#### Key Components:
- **MakeAppointmentUseCaseImpl**: A use case that checks if an appointment can be made for a truck, processes scheduling constraints, and coordinates between the `Truck` and external schedule providers.
- **Appointment Entity**: Manages appointment information, including `LicensePlate`, `MaterialType`, and `ArrivalWindow`.

---

### Warehouse Domain
The `Warehouse` domain includes management of inventory, order fulfillment, and truck docking for material delivery.

#### Key Components:
- **FulfillmentStatus**:
    - `OUTSTANDING`: Order yet to be fulfilled.
    - `FULFILLED`: Order completed.
    - `NONE`: No fulfillment required.

- **LicensePlate**: Represents a truck's license plate.

- **MaterialType**: Enum representing materials stored in the warehouse (`GYPSUM`, `IRON_ORE`, `CEMENT`, `PET_COKE`, `SLAG`).

- **OrderLine**: Contains order details, including material type, amount in tons, and price per ton.

- **PayloadDeliveryTicket**: Tracks delivery details, including dock assignment, delivery date, and warehouse ID.

- **PurchaseOrder**: Represents an order, with details on status, customer, and order lines.

- **Truck**: Manages truck-related data, including `LicensePlate`, `MaterialType`, and `DockNumber`.

- **Warehouse**: Manages warehouse operations, truck docking, and activity tracking.

---

### Commands and Events

#### Commands:
1. **DockTruckCommand**
    - Description: Handles the docking of a truck at the warehouse.
    - Fields:
        - `LicensePlate`: Truck's license plate.
        - `MaterialType`: Type of material being delivered.
        - `WarehouseId`: Identifier of the warehouse.
        - `DockNumber`: Dock number where the truck will dock.
        - `DeliveryDate`: Date of delivery.
        - `SellerId`: Identifier for the seller.

   ```java
   public record DockTruckCommand(
       LicensePlate licensePlate,
       MaterialType materialType,
       WarehouseId warehouseId,
       String dockNumber,
       LocalDateTime deliveryDate,
       UUID sellerId
   ) {
       // Constructor with validation
   }
   ```

2. **MakeAppointmentCommand**
    - Description: Creates an appointment for a truck to arrive at the warehouse.
    - Fields:
        - `LicensePlate`: Unique truck identifier.
        - `MaterialType`: Type of material.
        - `AppointmentWindowStart`: Start of the appointment window.
        - `AppointmentWindowEnd`: End of the appointment window.
        - `SellerId`: Identifier for the seller.

   ```java
   public record MakeAppointmentCommand(
       LicensePlate licensePlate,
       MaterialType materialType,
       LocalDateTime appointmentWindowStart,
       LocalDateTime appointmentWindowEnd,
       SellerId sellerId
   ) {
       // Constructor with validation
   }
   ```

3. **ReceiveWarehouseNumberCommand**
    - Description: Handles the reception of a warehouse number and weighbridge ticket.
    - Fields:
        - `LicensePlate`: Truck's license plate.
        - `NetWeight`: Net weight of the load.
        - `MaterialType`: Type of material delivered.
        - `SellerId`: Identifier for the seller.

   ```java
   public record ReceiveWarehouseNumberCommand(
       String licensePlate,
       BigDecimal netWeight,
       String materialType,
       UUID sellerId
   ) {
       // Constructor with validation
   }
   ```

4. **RecognizeTruckCommand**
    - Description: Recognizes a truck by its license plate and allows it to pass the gate.
    - Fields:
        - `LicensePlate`: Truck's license plate.
        - `MaterialType`: Type of material.
        - `DockNumber`: Dock number assigned to the truck.

   ```java
   public record RecognizeTruckCommand(
       String licensePlate,
       String materialType,
       String dockNumber
   ) {
       // Constructor with validation
   }
   ```

5. **GenerateWeighbridgeTicketCommand**
    - Description: Generates a weighbridge ticket for a truck leaving the warehouse.
    - Fields:
        - `LicensePlate`: Truck's license plate.
        - `GrossWeight`: Gross weight of the truck.
        - `TareWeight`: Tare weight of the truck.
        - `NetWeight`: Net weight of the load.

   ```java
   public record GenerateWeighbridgeTicketCommand(
       String licensePlate,
       BigDecimal grossWeight,
       BigDecimal tareWeight,
       BigDecimal netWeight
   ) {
       // Constructor with validation
   }
   ```

#### Events:
1. **AppointmentCreatedEvent**
    - Description: Triggered upon successful appointment creation for a truck.

   ```java
   public record AppointmentCreatedEvent(
       UUID sellerId,
       String licensePlate
   ) {
   }
   ```

2. **AppointmentUpdatedEvent**
    - Description: Triggered when an appointment is updated.

   ```java
   public record AppointmentUpdatedEvent(
       UUID sellerId,
       String licensePlate
   ) {
   }
   ```

3. **WarehouseActivityCreatedEvent**
    - Description: Triggered when a warehouse activity is created.

   ```java
   public record WarehouseActivityCreatedEvent(
       UUID warehouseId,
       WarehouseActivityType type,
       String materialType,
       UUID sellerId,
       LocalDateTime time,
       BigDecimal weight
   ) {
   }
   ```

4. **WBTUpdatedEvent**
    - Description: Triggered when a weighbridge ticket is updated.

   ```java
   public record WBTUpdatedEvent(
       String licensePlate,
       BigDecimal netWeight,
       UUID warehouseId
   ) {
   }
   ```

5. **WarehouseLoadedEvent**
    - Description: Triggered when materials are successfully loaded into the warehouse.

6. **WarehouseUnloadedEvent**
    - Description: Triggered when materials are successfully unloaded from the warehouse.

7. **OrderFulfilledEvent**
    - Description: Triggered when a purchase order is fulfilled.

---

### Domain Entities

#### Landside Domain
- **Truck**:
    - `LicensePlate`: Identifies the truck.
    - `MaterialType`: Specifies the material the truck carries.

#### Warehouse Domain
- **Warehouse**:
    - **Fields**:
        - `warehouseId`: Unique identifier.
        - `materialType`: Material stored.
        - `activities`: Tracks activities in the warehouse.
    - **Methods**:
        - `loadWarehouse(MaterialType, weight)`: Adds loading activity.
        - `unloadWarehouse(MaterialType, weight)`: Adds unloading activity.
        - `dockTruck(Truck)`: Docks a truck in the warehouse.
        - `generatePDT(Truck, dockNumber, deliveryDate)`: Generates a delivery ticket.
        - `assignWeighbridgeNumber(Truck)`: Assigns a weighbridge number to a truck.

- **OrderLine**:
    - Represents a line item in an order.
    - Fields: `MaterialType`, `AmountInTons`, `PricePerTon`.

- **PayloadDeliveryTicket**:
    - Tracks delivery information, including `LicensePlate`, `MaterialType`, `DockNumber`, and `WarehouseId`.

- **PurchaseOrder**:
    - Manages purchase order details.
    - Fields: `poNumber`, `CustomerNumber`, `Status`, `OrderLines`.

- **WarehouseActivity**:
    - Records a warehouse activity (e.g., loading, unloading).
    - Fields: `WarehouseActivityId`, `Type`, `Time`, `MaterialType`, `Weight`.

- **WarehouseActivityWindow**:
    - Manages all activities within a specific time frame.
    - Methods:
        - `addActivity(type, sellerId, materialType, weight, status)`: Records a new activity.

- **FulfillmentStatus**:
    - Enum to manage fulfillment states: `OUTSTANDING`, `FULFILLED`, `NONE`.

---

This **README** provides an overview of the structure and operations within the `Landside` and `Warehouse` domains, detailing commands, events, and domain classes to support core functionalities. The modular design ensures easy extension and integration across different functional areas.

--- 
