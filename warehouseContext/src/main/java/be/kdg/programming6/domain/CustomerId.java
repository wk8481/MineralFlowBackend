package be.kdg.programming6.domain;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID id) {
    public CustomerId {
        Objects.requireNonNull(id, "Id cannot be null");
    }
}

