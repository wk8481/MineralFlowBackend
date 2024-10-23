package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.warehouse.domain.OrderLine;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.port.in.PurchaseOrderUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class PurchaseOrderController {
    private final PurchaseOrderUseCase purchaseOrderUseCase;

    public PurchaseOrderController(PurchaseOrderUseCase purchaseOrderUseCase) {
        this.purchaseOrderUseCase = purchaseOrderUseCase;
    }

    @GetMapping("/purchase-orders")
    public List<POReceivedDTO.PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderUseCase.getAllPurchaseOrders().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private POReceivedDTO.PurchaseOrder convertToDTO(PurchaseOrder purchaseOrder) {
        POReceivedDTO.PurchaseOrder dto = new POReceivedDTO.PurchaseOrder();
        dto.setDate(purchaseOrder.getDate());
        dto.setPoNumber(purchaseOrder.getPoNumber());
        dto.setCustomerNumber(purchaseOrder.getCustomerNumber());
        dto.setCustomerName(purchaseOrder.getCustomerName());
        dto.setStatus(POReceivedDTO.PurchaseOrderStatus.valueOf(purchaseOrder.getStatus().name())); // Convert status
        dto.setOrderLines(purchaseOrder.getOrderLines().stream()
                .map(this::convertOrderLineToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private POReceivedDTO.OrderLine convertOrderLineToDTO(OrderLine orderLine) {
        POReceivedDTO.OrderLine dto = new POReceivedDTO.OrderLine();
        dto.setMaterialType(orderLine.getMaterialType().name());
        dto.setAmountInTons(orderLine.getAmountInTons());
        dto.setPricePerTon(orderLine.getPricePerTon());
        return dto;
    }
}