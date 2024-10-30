package be.kdg.prgramming6.warehouse.domain;

import be.kdg.prgramming6.common.domain.WarehouseActivityType;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WarehouseActivity( WarehouseActivityId activityId, LocalDateTime time, WarehouseActivityType warehouseActivityType, SellerId sellerId, MaterialType materialType, BigDecimal weight, FulfillmentStatus fulfillmentStatus) {

    public WarehouseActivity {
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
        if (warehouseActivityType == null) {
            throw new IllegalArgumentException("Warehouse activity type cannot be null");
        }
        if (sellerId == null) {
            throw new IllegalArgumentException("Seller ID cannot be null");
        }
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null");
        }

        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null");
        }
        if (fulfillmentStatus == null) {
            throw new IllegalArgumentException("Fulfillment status cannot be null");


        }
    }

        public BigDecimal getChangeToCapacity() {
            return switch (warehouseActivityType) {
                case DELIVERY -> weight;
                case SHIPMENT-> weight.negate();
            };

    }
}
