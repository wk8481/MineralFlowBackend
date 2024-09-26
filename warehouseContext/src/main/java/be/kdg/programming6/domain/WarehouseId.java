package be.kdg.programming6.domain;

import java.util.Objects;

public record WarehouseId(String value) {
    public WarehouseId {
        Objects.requireNonNull(value, "Warehouse ID cannot be null");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Warehouse ID cannot be empty");
        }
    }
}
