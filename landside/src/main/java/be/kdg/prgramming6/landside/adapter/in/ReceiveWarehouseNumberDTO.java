package be.kdg.prgramming6.landside.adapter.in;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class ReceiveWarehouseNumberDTO {
    @NotNull
    private String licensePlate;

    @NotNull
    private BigDecimal netWeight;

    @NotNull
    private String materialType;

    @NotNull
    private UUID sellerId;

    // Constructor with parameters
    public ReceiveWarehouseNumberDTO(String licensePlate, BigDecimal netWeight, String materialType, UUID sellerId) {
        this.licensePlate = licensePlate;
        this.netWeight = netWeight;
        this.materialType = materialType;
        this.sellerId = sellerId;
    }

    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }
}