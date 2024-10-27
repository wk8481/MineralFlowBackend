# Project Overview

This project is a Spring Boot application that manages warehouse and landside operations. It includes various use cases, controllers, and events to handle different aspects of the system.

## Table of Contents

- [Commands](#commands)
- [Events](#events)
- [HTTP Tests](#http-tests)
- [Running Tests](#running-tests)

## Commands
Commands are actions that change the state of the system. They are typically initiated by the user or an external system and are handled by the application to perform specific tasks.


### `DockTruckCommand`

Handles the docking of a truck at the warehouse.

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

### `MakeAppointmentCommand`
Creates an appointment for a truck to arrive at the warehouse.

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

### `ReceiveWarehouseNumberCommand`
Handles the reception of a warehouse number and a weighbridge ticket as well as creating the partial wbt.

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

### `RecognizeTruckCommand`
Recognizes a truck by its license plate and allows it to pass the gate and allow it be given the access to dock

```java
public record RecognizeTruckCommand(
    String licensePlate,
    String materialType,
    String dockNumber
) {
    // Constructor with validation
}
```

### `GenerateWeighbridgeTicketCommand`
Generates a weighbridge ticket for a truck that is leaving the warehouse

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
## Events
Events are notifications that something has happened in the system, They are typically used to inform other parts of the system or external systems about changes in state.

### `AppointmentUpdatedEvent`
This event is triggered when an appointment is updated but is just for the event publishing example

```java
public record AppointmentUpdatedEvent(
    UUID sellerId,
    String licensePlate
) {
}
```

### `WarehouseActivityCreatedEvent`
This event is triggered when a warehouse activity is created

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

### `WBTUpdatedEvent`
This event is triggered when a weighbridge ticket is updated and sends the weight to the warehouselistener to kick off the event sourcing

```java
public record WBTUpdatedEvent(
    String licensePlate,
    BigDecimal netWeight,
    UUID warehouseId
) {
}
```

## HTTP Tests

### `Get Access Token`
```shell
POST http://localhost:8180/realms/mineral/protocol/openid-connect/token HTTP/1.1
Content-Type: application/x-www-form-urlencoded

client_id=backend&client_secret=bQG1f1VFFPbGkF8sjtMPryOXQChtU8p4&username=zamlamb&password=admin&grant_type=password&scope=openid

> {%
    client.global.set("access_token", response.body.access_token);
%}
```

### `Vali Request Appointment`
```shell
POST http://localhost:8090/api/make-appointment
Authorization: Bearer {{access_token}}
Content-Type: application/json

{
  "sellerId": "dd2e2b7d-4622-441f-9200-123f8bb3e29b",
  "licensePlate": "ABC-123",
  "materialType": "PET_COKE",
  "appointmentWindowStart": "2024-10-30T10:00:00",
  "appointmentWindowEnd": "2024-10-30T10:59:00"
}
```


### `Recognize Truck`
```shell
POST http://localhost:8090/api/recognize-truck
Authorization: Bearer {{access_token}}
Content-Type: application/json

{
  "licensePlate": "ABC-123",
  "materialType": "PET_COKE",
  "dockNumber": "WD-10"
}
```

### `Receive Warehouse Number and Weighbridge Number`
```shell
POST http://localhost:8090/api/receive-warehouse-number
Authorization: Bearer {{access_token}}
Content-Type: application/json

{
  "licensePlate": "ABC-123",
  "netWeight": 3000,
  "materialType": "PET_COKE",
  "sellerId": "dd2e2b7d-4622-441f-9200-123f8bb3e29b"
}
```

### `Dock Truck`
```shell
POST http://localhost:8090/api/dock-truck
Authorization: Bearer {{access_token}}
Content-Type: application/json

{
  "licensePlate": "ABC-123",
  "materialType": "PET_COKE",
  "warehouseId": "e2f7bcdc-69da-4b0e-b73f-6adf79e8d5f9",
  "sellerId": "dd2e2b7d-4622-441f-9200-123f8bb3e29b",
  "deliveryDate": "2024-10-30T09:00:00",
  "dockNumber": "WD-10"
}
```

### `Generate Weighbridge Ticket`
```shell
POST http://localhost:8090/api/generate-weighbridge-ticket
Authorization: Bearer {{access_token}}
Content-Type: application/json

{
  "licensePlate": "ABC-123",
  "grossWeight": 13000,
  "tareWeight": 10000,
  "netWeight": 3000
}
```

### `Get Trucks On Time / Check Arrival`
```shell
GET http://localhost:8090/api/check-arrival
Authorization: Bearer {{access_token}}
Accept: application/json
Content-Type: application/json
```

### `Get One Truck / Check Arrival by License Plate`
```shell
GET http://localhost:8090/api/check-arrival/ABC-001
Authorization: Bearer {{access_token}}
```

### `Get Trucks On Site`
```shell
GET http://localhost:8090/api/trucks-on-site
Authorization: Bearer {{access_token}}
Content-Type: application/json
```

### `PO Rabbit`
```shell
POST http://localhost:15672/api/exchanges/%2f/purchase_order_exchange/publish
Authorization: Basic bXl1c2VyOm15cGFzc3dvcmQ=
Content-Type: application/json

{
  "properties": {},
  "routing_key": "purchase.order.created",
  "payload": "{\"purchaseOrder\":{\"date\":\"2024-10-23T13:31:00\",\"poNumber\":\"PO123456\",\"customerNumber\":\"550e8400-e29b-41d4-a716-446655440000\",\"customerName\":\"Joske Vermeulen\",\"status\":\"OUTSTANDING\",\"orderLines\":[{\"materialType\":\"PET_COKE\",\"amountInTons\":100,\"pricePerTon\":50.0},{\"materialType\":\"SLAG\",\"amountInTons\":50,\"pricePerTon\":30.0}]}}",
  "payload_encoding": "string"
}
```

### `Get Purchase Orders`
```shell
GET http://localhost:8090/api/purchase-orders
Authorization: Bearer {{access_token}}
Content-Type: application/json
```

### `Get Purchase Order by ID`
```shell
GET http://localhost:8090/api/purchase-orders/PO123456
Authorization: Bearer {{access_token}}
```

### `Get Warehouses with Total Material Weight`
```shell
GET http://localhost:8090/api/total-material
Authorization: Bearer {{access_token}}
Content-Type: application/json
```

## Running Tests
This section will be updated once the tests in the test folders are completed.

```shell
# Example command to run tests
./gradlew test
```
```

