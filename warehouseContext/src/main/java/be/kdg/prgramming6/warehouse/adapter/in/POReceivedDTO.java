package be.kdg.prgramming6.warehouse.adapter.in;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class POReceivedDTO {
    private PurchaseOrder purchaseOrder;

    public static class PurchaseOrder {
        private Date date;
        private String poNumber;
        private UUID customerNumber;
        private String customerName;
        private List<OrderLine> orderLines;
        private PurchaseOrderStatus status; // Add status field

        // Getters and setters
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getPoNumber() {
            return poNumber;
        }

        public void setPoNumber(String poNumber) {
            this.poNumber = poNumber;
        }

        public UUID getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(UUID customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public List<OrderLine> getOrderLines() {
            return orderLines;
        }

        public void setOrderLines(List<OrderLine> orderLines) {
            this.orderLines = orderLines;
        }

        public PurchaseOrderStatus getStatus() {
            return status;
        }

        public void setStatus(PurchaseOrderStatus status) {
            this.status = status;
        }
    }

    public static class OrderLine {
        private String materialType;
        private int amountInTons;
        private BigDecimal pricePerTon;

        // Getters and setters
        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public int getAmountInTons() {
            return amountInTons;
        }

        public void setAmountInTons(int amountInTons) {
            this.amountInTons = amountInTons;
        }

        public BigDecimal getPricePerTon() {
            return pricePerTon;
        }

        public void setPricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
        }
    }

    public enum PurchaseOrderStatus {
        FULFILLED,
        OUTSTANDING
    }

    // Getters and setters
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}