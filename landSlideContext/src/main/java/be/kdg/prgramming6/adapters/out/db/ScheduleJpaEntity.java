package be.kdg.prgramming6.adapters.out.db;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList; // Import ArrayList
import java.util.List;

@Entity
@Table(catalog = "landside", name = "schedule")
public class ScheduleJpaEntity {

    @Id
    @Column(nullable = false, unique = true)
    private LocalDate day; // Use LocalDate as the primary key

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentJpaEntity> appointments = new ArrayList<>(); // Initialize the list

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
        this.appointments = appointments != null ? appointments : new ArrayList<>(); // Avoid null assignments
    }
}
