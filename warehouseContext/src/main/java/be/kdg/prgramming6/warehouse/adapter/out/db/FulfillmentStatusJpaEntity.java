package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.FulfillmentStatus;
import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse", name = "fulfillment_status")
public class FulfillmentStatusJpaEntity {
    @Id
    @EmbeddedId
    private WarehouseActivityJpaId id;

    @OneToOne
    @MapsId
    @JoinColumns({
            @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id"),
            @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    })
    private WarehouseActivityJpaEntity activity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FulfillmentStatus status;

    // Constructors, getters, setters
    public FulfillmentStatusJpaEntity() {
    }

    public FulfillmentStatusJpaEntity(WarehouseActivityJpaEntity activity, boolean isFulfilled) {
        this.id = activity.getId();
        this.activity = activity;
        this.status = isFulfilled ? FulfillmentStatus.FULFILLED : FulfillmentStatus.OUTSTANDING;
    }

    public WarehouseActivityJpaId getId() {
        return id;
    }

    public void setId(WarehouseActivityJpaId id) {
        this.id = id;
    }

    public WarehouseActivityJpaEntity getActivity() {
        return activity;
    }

    public void setActivity(WarehouseActivityJpaEntity activity) {
        this.activity = activity;
    }

    public FulfillmentStatus getStatus() {
        return status;
    }

    public void setStatus(FulfillmentStatus status) {
        this.status = status;
    }
}