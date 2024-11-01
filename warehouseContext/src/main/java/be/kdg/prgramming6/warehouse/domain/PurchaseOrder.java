package be.kdg.prgramming6.warehouse.domain;


import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PurchaseOrder {
    private final Date date;
    private final String poNumber;
    private final UUID customerNumber;
    private final String customerName;
    private PurchaseOrderStatus status;
    private final List<OrderLine> orderLines;

    public PurchaseOrder(Date date, String poNumber, UUID customerNumber, String customerName, PurchaseOrderStatus status, List<OrderLine> orderLines) {
        this.date = date;
        this.poNumber = poNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.status = status;
        this.orderLines = orderLines;
    }



    public Date getDate() {
        return date;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public String getCustomerName() {
        return customerName;
    }



    public UUID getCustomerNumber() {
        return customerNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public boolean hasStatus(PurchaseOrderStatus status) {
        return this.status == status;
    }

    public void updateStatus(PurchaseOrderStatus purchaseOrderStatus) {
        this.status = purchaseOrderStatus;
    }

    public boolean matches(PurchaseOrder receivedOrder) {
        return this.poNumber.equals(receivedOrder.getPoNumber()) &&
                this.customerNumber.equals(receivedOrder.getCustomerNumber()) &&
                this.customerName.equals(receivedOrder.getCustomerName()) &&
                this.date.equals(receivedOrder.getDate()) &&
                this.orderLines.equals(receivedOrder.getOrderLines());
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }
}