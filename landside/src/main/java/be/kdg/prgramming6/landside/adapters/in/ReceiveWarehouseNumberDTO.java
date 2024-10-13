package be.kdg.prgramming6.landside.adapters.in;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ReceiveWarehouseNumberDTO {
    @NotNull
    private String licensePlate;

    @NotNull
    private UUID sellerId;

    @NotNull
    private String materialType;

    // Default constructor needed for deserialization
    public ReceiveWarehouseNumberDTO() {
    }

    // Constructor with parameters
    public ReceiveWarehouseNumberDTO(String licensePlate, UUID sellerId, String materialType) {
        this.licensePlate = licensePlate;
        this.sellerId = sellerId;
        this.materialType = materialType;
    }

    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}