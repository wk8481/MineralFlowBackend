package be.kdg.prgramming6.warehouse.adapter.in;

import be.kdg.prgramming6.warehouse.domain.MaterialType;
import be.kdg.prgramming6.warehouse.domain.OrderLine;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrderStatus;
import be.kdg.prgramming6.warehouse.port.in.UpdatePurchaseOrderUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class POReceiveListener {

    private static final Logger logger = LoggerFactory.getLogger(POReceiveListener.class);
    private static final String PURCHASE_ORDER_QUEUE = "purchase_order_queue";
    private final UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;

    public POReceiveListener(UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase) {

        this.updatePurchaseOrderUseCase = updatePurchaseOrderUseCase;
    }

    @RabbitListener(queues = PURCHASE_ORDER_QUEUE)
    public void receiveMessage(final POReceivedDTO poReceivedDTO) {
        try {
            // Process the valid message
            logger.info("Received and validated message: {}", poReceivedDTO);

            // Convert DTO to domain object
            PurchaseOrder receivedOrder = convertToDomain(poReceivedDTO.getPurchaseOrder());

            // Handle the purchase order
            updatePurchaseOrderUseCase.handle(receivedOrder);


        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    private PurchaseOrder convertToDomain(POReceivedDTO.PurchaseOrder dto) {
        return new PurchaseOrder(
                dto.getDate(),
                dto.getPoNumber(),
                dto.getCustomerNumber(),
                dto.getCustomerName(),
                PurchaseOrderStatus.valueOf(dto.getStatus().name()), // Convert status
                dto.getOrderLines().stream()
                        .map(this::convertOrderLineToDomain)
                        .collect(Collectors.toList())
        );
    }

    private OrderLine convertOrderLineToDomain(POReceivedDTO.OrderLine dto) {
        return new OrderLine(
                MaterialType.fromString(dto.getMaterialType()),
                dto.getAmountInTons(),
                dto.getPricePerTon()
        );
    }
}