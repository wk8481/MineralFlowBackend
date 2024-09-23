package be.kdg.prgramming6.domain;

import java.util.Objects;
import java.util.UUID;

public record SellerId(UUID id) {
    public SellerId {
        Objects.requireNonNull(id, "Id cannot be null");
    }
}

