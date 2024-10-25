package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.WarehouseActivityId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class WarehouseActivityJpaId implements Serializable {
    @Column(name = "warehouse_id")
    private UUID warehouseId;

    @Column(name = "activity_id")
    private UUID activityId;

    public WarehouseActivityJpaId(final UUID warehouseId, final UUID activityId) {
        this.warehouseId = warehouseId;
        this.activityId = activityId;
    }

    public WarehouseActivityJpaId() {
    }

    public static WarehouseActivityJpaId of(final WarehouseActivityId activityId) {
        return new WarehouseActivityJpaId(activityId.warehouseId().id(), activityId.activityId());
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public UUID getActivityId() {
        return activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseActivityJpaId that = (WarehouseActivityJpaId) o;
        return Objects.equals(warehouseId, that.warehouseId) && Objects.equals(activityId, that.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, activityId);
    }
}
