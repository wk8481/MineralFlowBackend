package be.kdg.prgramming6.landside.domain;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Warehouse {
    private final WarehouseId warehouseId;
    private final MaterialType materialType;
    private static final BigDecimal MAX_CAPACITY = BigDecimal.valueOf(500_000); // Maximum capacity in tonnes
    private static final BigDecimal OVERFLOW_LIMIT = MAX_CAPACITY.multiply(BigDecimal.valueOf(1.1)); // 10% overflow limit
    private BigDecimal currentCapacity;
    private final SellerId sellerId;
    private String dockNumber; // Additional attribute for dock number

    public Warehouse(WarehouseId warehouseId, MaterialType materialType, SellerId sellerId) {
        this.warehouseId = Objects.requireNonNull(warehouseId, "Warehouse ID cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.currentCapacity = BigDecimal.ZERO; // Initialize the current load as empty
        this.sellerId = Objects.requireNonNull(sellerId, "Seller ID cannot be null");
    }

    // Full constructor including dockNumber
    public Warehouse(WarehouseId warehouseId, MaterialType materialType, SellerId sellerId, String dockNumber) {
        this.warehouseId = Objects.requireNonNull(warehouseId, "Warehouse ID cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.currentCapacity = BigDecimal.ZERO; // Initialize the current load as empty
        this.sellerId = Objects.requireNonNull(sellerId, "Seller ID cannot be null");
        this.dockNumber = dockNumber; // Can be null initially
    }

    public Warehouse(WarehouseId warehouseId, MaterialType materialType, SellerId sellerId, BigDecimal currentCapacity) {
        this.warehouseId = Objects.requireNonNull(warehouseId, "Warehouse ID cannot be null");
        this.materialType = Objects.requireNonNull(materialType, "Material type cannot be null");
        this.currentCapacity = Objects.requireNonNull(currentCapacity, "Current capacity cannot be null"); // Use the provided current capacity
        this.sellerId = Objects.requireNonNull(sellerId, "Seller ID cannot be null");
    }

    // Getters
    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public SellerId getSellerId() {
        return sellerId;
    }

    public BigDecimal getCurrentCapacity() {
        return currentCapacity;
    }

    public static BigDecimal getMaxCapacity() {
        return MAX_CAPACITY;
    }


    public synchronized void modifyCapacity(final WarehouseActivityType warehouseActivityType, final BigDecimal weight) {
        if (weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }
        switch (warehouseActivityType) {
            case DELIVERY -> store(weight);
            case SHIPMENT -> {
                if (!canUnload(weight)) {
                    throw new IllegalStateException("Warehouse does not have enough capacity for shipment.");
                }
                currentCapacity = currentCapacity.subtract(weight);
            }
        }
    }

    // Method to check if there is enough storage available for the given payload weight
    public boolean canStore(BigDecimal payloadWeight) {
        return currentCapacity.add(payloadWeight).compareTo(OVERFLOW_LIMIT) <= 0;
    }

    // Method to check if there is enough capacity for unloading the given weight
    public boolean canUnload(BigDecimal weight) {
        return currentCapacity.compareTo(weight) >= 0;
    }

    // Method to store the payload (updating the current capacity)
    public void store(BigDecimal weight) {
        if (!canStore(weight)) {
            throw new IllegalStateException("Warehouse has exceeded its maximum capacity including overflow limit.");
        }
        currentCapacity = currentCapacity.add(weight);
    }


    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", materialType=" + materialType +
                ", currentCapacity=" + currentCapacity +
                ", sellerId=" + sellerId +
                ", dockNumber='" + dockNumber + '\'' +
                '}';
    }
}