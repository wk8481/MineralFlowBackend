package be.kdg.prgramming6.warehouse.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Capacity(LocalDateTime time, BigDecimal weight) {
}
