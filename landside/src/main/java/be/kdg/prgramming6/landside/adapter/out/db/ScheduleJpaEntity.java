package be.kdg.prgramming6.landside.adapter.out.db;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(catalog = "landside", name = "schedule")
public class ScheduleJpaEntity {

    @Id
    @Column(name = "schedule_time",  nullable = false, unique = true)
    private LocalDateTime scheduleTime; // Use LocalDate as the primary key

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentJpaEntity> appointments = new ArrayList<>(); // Initialize the list

    // Getters and setters
    public LocalDateTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalDateTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public List<AppointmentJpaEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentJpaEntity> appointments) {
        this.appointments = appointments != null ? appointments : new ArrayList<>(); // Avoid null assignments
    }
}
