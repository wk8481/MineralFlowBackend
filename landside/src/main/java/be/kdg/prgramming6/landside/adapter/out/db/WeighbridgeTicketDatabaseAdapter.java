package be.kdg.prgramming6.landside.adapter.out.db;

import be.kdg.prgramming6.landside.domain.WarehouseId;
import be.kdg.prgramming6.landside.domain.WeighbridgeTicket;
import be.kdg.prgramming6.landside.port.out.LoadWBTPort;
import be.kdg.prgramming6.landside.port.out.SavePartialWBTport;
import be.kdg.prgramming6.landside.port.out.SaveWBTPort;
import be.kdg.prgramming6.landside.port.out.UpdateWBTPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class WeighbridgeTicketDatabaseAdapter implements SaveWBTPort, LoadWBTPort, UpdateWBTPort, SavePartialWBTport {
    private final static Logger LOGGER = LoggerFactory.getLogger(WeighbridgeTicketDatabaseAdapter.class.getName());

    private final WeighbridgeTicketJpaRepository repository;

    public WeighbridgeTicketDatabaseAdapter(WeighbridgeTicketJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(WeighbridgeTicket ticket) {
        WeighbridgeTicketJpaEntity entity = new WeighbridgeTicketJpaEntity();
        entity.setLicensePlate(ticket.getLicensePlate());
        entity.setGrossWeight(ticket.getGrossWeight());
        entity.setTareWeight(ticket.getTareWeight());
        entity.setNetWeight(ticket.getNetWeight());
        entity.setTimestamp(ticket.getTimestamp());
        repository.save(entity);
    }

    @Override
    public Optional<WeighbridgeTicket> loadByLicensePlate(String licensePlate) {
        return repository.findByLicensePlate(licensePlate).map(this::toWBT);
    }

    @Override
    public void update(WeighbridgeTicket ticket, WarehouseId warehouseId) {
        LOGGER.info("Updating weighbridge ticket for license plate: {}", ticket.getLicensePlate());

        WeighbridgeTicketJpaEntity entity = repository.findByLicensePlate(ticket.getLicensePlate())
                .orElseThrow(() -> new IllegalArgumentException("Weighbridge ticket not found"));
        entity.setGrossWeight(ticket.getGrossWeight());
        entity.setTareWeight(ticket.getTareWeight());
        entity.setNetWeight(ticket.getNetWeight());
        entity.setTimestamp(ticket.getTimestamp());
        repository.save(entity);
    }

    @Override
    public void savePartial(WeighbridgeTicket ticket) {
        WeighbridgeTicketJpaEntity entity = repository.findByLicensePlate(ticket.getLicensePlate())
                .orElseGet(() -> {
                    WeighbridgeTicketJpaEntity newEntity = new WeighbridgeTicketJpaEntity();
                    newEntity.setLicensePlate(ticket.getLicensePlate());
                    newEntity.setTimestamp(LocalDateTime.now());
                    return newEntity;
                });

        if (ticket.getGrossWeight() != null) {
            entity.setGrossWeight(ticket.getGrossWeight());
        }
        if (ticket.getTareWeight() != null) {
            entity.setTareWeight(ticket.getTareWeight());
        }
        if (ticket.getNetWeight() != null) {
            entity.setNetWeight(ticket.getNetWeight());
        }
        repository.save(entity);
    }

    private WeighbridgeTicket toWBT(WeighbridgeTicketJpaEntity entity) {
        BigDecimal netWeight = entity.getNetWeight();
        if (netWeight == null) {
            netWeight = entity.getGrossWeight().subtract(entity.getTareWeight());
        }
        return new WeighbridgeTicket(
                entity.getLicensePlate(),
                entity.getGrossWeight(),
                entity.getTareWeight(),
                netWeight,
                entity.getTimestamp()
        );
    }
}