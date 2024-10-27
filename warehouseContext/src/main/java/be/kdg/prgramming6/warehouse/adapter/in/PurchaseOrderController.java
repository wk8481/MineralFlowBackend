package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.warehouse.domain.OrderLine;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.port.in.GetListPurchaseOrderUseCase;
import be.kdg.prgramming6.warehouse.port.in.GetPurchaseOrderByPoNumberUseCase;
import be.kdg.prgramming6.warehouse.port.in.UpdatePurchaseOrderUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class PurchaseOrderController {
    private final GetListPurchaseOrderUseCase getListPurchaseOrderUseCase;
    private final GetPurchaseOrderByPoNumberUseCase getPurchaseOrderByPoNumberUseCase;

    public PurchaseOrderController(GetListPurchaseOrderUseCase getListPurchaseOrderUseCase, GetPurchaseOrderByPoNumberUseCase getPurchaseOrderByPoNumberUseCase) {
        this.getListPurchaseOrderUseCase = getListPurchaseOrderUseCase;
        this.getPurchaseOrderByPoNumberUseCase = getPurchaseOrderByPoNumberUseCase;
    }

    @GetMapping("/purchase-orders")
    public List<POReceivedDTO.PurchaseOrder> getAllPurchaseOrders() {
        return getListPurchaseOrderUseCase.getAllPurchaseOrders().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/purchase-orders/{poNumber}")
    public POReceivedDTO.PurchaseOrder getPurchaseOrderByPoNumber(@PathVariable String poNumber) {
        PurchaseOrder purchaseOrder = getPurchaseOrderByPoNumberUseCase.getPurchaseOrderByPoNumber(poNumber);
        return convertToDTO(purchaseOrder);
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