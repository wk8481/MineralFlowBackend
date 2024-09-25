package be.kdg.prgramming6.adapters.out.db;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedule")
public class ScheduleJpaEntity {

    @Id
    @Column(nullable = false, unique = true)
    private LocalDate day; // Use LocalDate as the primary key

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentJpaEntity> appointments;

    // Getters and setters
    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<AppointmentJpaEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentJpaEntity> appointments) {
        this.appointments = appointments;
    }
}
