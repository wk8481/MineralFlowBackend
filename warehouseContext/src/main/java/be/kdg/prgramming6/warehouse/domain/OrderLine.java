package be.kdg.prgramming6.warehouse.domain;

import java.math.BigDecimal;

public class OrderLine {
    private final MaterialType materialType;
    private final int amountInTons;
    private final BigDecimal pricePerTon;

    public OrderLine(MaterialType materialType, int amountInTons, BigDecimal pricePerTon) {
        this.materialType = materialType;
        this.amountInTons = amountInTons;
        this.pricePerTon = pricePerTon;
    }


    // Getters and setters
    public MaterialType getMaterialType() {
        return materialType;
    }


    public int getAmountInTons() {
        return amountInTons;
    }



    public BigDecimal getPricePerTon() {
        return pricePerTon;
    }


}