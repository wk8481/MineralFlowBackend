package be.kdg.prgramming6.warehouse.adapter.out.db;

import be.kdg.prgramming6.warehouse.domain.OrderLine;
import be.kdg.prgramming6.warehouse.domain.PurchaseOrder;
import be.kdg.prgramming6.warehouse.port.out.LoadAllPurchaseOrdersPort;
import be.kdg.prgramming6.warehouse.port.out.LoadPurchaseOrderByIdPort;
import be.kdg.prgramming6.warehouse.port.out.SavePurchaseOrderPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderDatabaseAdapter implements LoadPurchaseOrderByIdPort, SavePurchaseOrderPort, LoadAllPurchaseOrdersPort {
    private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;

    public PurchaseOrderDatabaseAdapter(PurchaseOrderJpaRepository purchaseOrderJpaRepository) {
        this.purchaseOrderJpaRepository = purchaseOrderJpaRepository;
    }

    @Override
    public Optional<PurchaseOrder> loadPurchaseOrderById(String poNumber) {
        return purchaseOrderJpaRepository.findById(poNumber).map(this::convertToDomain);
    }



    @Override
    public List<PurchaseOrder> loadAllPurchaseOrders() {
        return purchaseOrderJpaRepository.findAll().stream()
                .map(this::convertToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderJpaRepository.save(convertToEntity(purchaseOrder));
    }

    private PurchaseOrder convertToDomain(PurchaseOrderJpaEntity entity) {
        return new PurchaseOrder(
                entity.getDate(),
                entity.getPoNumber(),
                entity.getCustomerNumber(),
                entity.getCustomerName(),
                entity.getStatus(),
                entity.getOrderLines().stream()
                        .map(this::convertOrderLineToDomain)
                        .collect(Collectors.toList())
        );
    }

    private OrderLine convertOrderLineToDomain(OrderLineJpaEntity entity) {
        return new OrderLine(
                entity.getMaterialType(),
                entity.getAmountInTons(),
                entity.getPricePerTon()
        );
    }

    private PurchaseOrderJpaEntity convertToEntity(PurchaseOrder domain) {
        PurchaseOrderJpaEntity entity = new PurchaseOrderJpaEntity();
        entity.setPoNumber(domain.getPoNumber());
        entity.setDate(domain.getDate());
        entity.setCustomerNumber(domain.getCustomerNumber());
        entity.setCustomerName(domain.getCustomerName());
        entity.setStatus(domain.getStatus());
        entity.setOrderLines(domain.getOrderLines().stream()
                .map(this::convertOrderLineToEntity)
                .collect(Collectors.toList()));
        return entity;
    }

    private OrderLineJpaEntity convertOrderLineToEntity(OrderLine domain) {
        OrderLineJpaEntity entity = new OrderLineJpaEntity();
        entity.setMaterialType(domain.getMaterialType());
        entity.setAmountInTons(domain.getAmountInTons());
        entity.setPricePerTon(domain.getPricePerTon());
        return entity;
    }
}