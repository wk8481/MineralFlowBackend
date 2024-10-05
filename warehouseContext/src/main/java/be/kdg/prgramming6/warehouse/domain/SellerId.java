package be.kdg.prgramming6.warehouse.domain;

import java.util.Objects;
import java.util.UUID;

public record SellerId(UUID id) {
    // Constructor
    public SellerId {
        Objects.requireNonNull(id, "Id cannot be null");
    }
}
