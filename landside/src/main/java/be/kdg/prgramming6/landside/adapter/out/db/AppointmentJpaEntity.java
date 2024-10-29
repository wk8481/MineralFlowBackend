package be.kdg.prgramming6.landside.adapter.out.db;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "landside", name = "appointment")
public class AppointmentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    @ManyToOne
    @JoinColumn(name = "schedule_time", nullable = false)
    private ScheduleJpaEntity schedule;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "material_type", nullable = false)
    private String materialType;

    @Column(name = "window_start", nullable = false)
    private LocalDateTime windowStart;

    @Column(name = "window_end", nullable = false)
    private LocalDateTime windowEnd;

    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    public AppointmentJpaEntity(ScheduleJpaEntity schedule, String licensePlate, String materialType, LocalDateTime windowStart, LocalDateTime windowEnd, UUID sellerId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.schedule = schedule;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.sellerId = sellerId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public AppointmentJpaEntity() {
    }


    // Getters and setters...

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public LocalDateTime getWindowStart() {
        return windowStart;
    }

    public void setWindowStart(LocalDateTime windowStart) {
        this.windowStart = windowStart;
    }

    public LocalDateTime getWindowEnd() {
        return windowEnd;
    }

    public void setWindowEnd(LocalDateTime windowEnd) {
        this.windowEnd = windowEnd;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public ScheduleJpaEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleJpaEntity schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}