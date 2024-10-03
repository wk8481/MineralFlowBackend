package be.kdg.prgramming6.domain;

import java.util.Objects;
import java.util.UUID;

public record WarehouseId(UUID id) {
    public WarehouseId {
        Objects.requireNonNull(id, "Warehouse ID cannot be null");
        // No need for isEmpty check, as UUID cannot be empty
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
