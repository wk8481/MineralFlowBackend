package be.kdg.prgramming6.domain;

import java.util.Objects;
import java.util.UUID;

public record PersonId(UUID id) {
    public PersonId {
        Objects.requireNonNull(id, "Id cannot be null");
    }
}

