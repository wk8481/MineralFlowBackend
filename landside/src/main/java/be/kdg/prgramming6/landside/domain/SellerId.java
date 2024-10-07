package be.kdg.prgramming6.landside.domain;

import java.util.Objects;
import java.util.UUID;

public record SellerId(UUID id) {
    public SellerId {
        Objects.requireNonNull(id, "Id cannot be null");
    }

    public UUID getValue() {
        return id;
    }
}

