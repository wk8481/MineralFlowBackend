//package be.kdg.prgramming6.adapters.out.db;
//
//import be.kdg.prgramming6.domain.*;
//import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class AppointmentDatabaseAdapter implements UpdateAppointmentPort {
//    private final Logger logger = LoggerFactory.getLogger(AppointmentDatabaseAdapter.class);
//    private final ScheduleJpaEntity scheduleJpaRepository;
//
//    public AppointmentDatabaseAdapter(ScheduleJpaEntity scheduleJpaRepository) {
//        this.scheduleJpaRepository = scheduleJpaRepository;
//    }
//
//    @Override
//    public void updateAppointment(Appointment appointment) {
//
//    }
//
//    //Load schedule, ask schdule to schedule an appoinment and convert to domain object
//
//    private Schedule toSchedule(final ScheduleJpaEntity scheduleJpaEntity) {
//        Schedule schedule = new Schedule();  // Create a new domain Schedule object
//
//        // Map each AppointmentJpaEntity to a domain Appointment and schedule it in the domain Schedule
//        scheduleJpaEntity.getAppointments().forEach(appointmentJpaEntity -> {
//            Appointment appointment = toAppointment(appointmentJpaEntity); // Convert JPA entity to domain object
//            schedule.scheduleAppointment(
//                    appointment.sellerId(),
//                    appointment.truck().licensePlate(),
//                    appointment.materialType(),
//                    appointment.windowStart(),
//                    appointment.windowEnd()
//            );
//        });
//
//        return schedule;  // Return the populated Schedule object
//    }
//
//
//    private Appointment toAppointment(final AppointmentJpaEntity appointmentJpaEntity) {
//        // Convert and return the Appointment domain object
//        return new Appointment(
//                appointmentJpaEntity.getAppointmentId(),
//                new Truck(new LicensePlate(appointmentJpaEntity.getLicensePlate())), // Assuming Truck takes a LicensePlate
//                MaterialType.valueOf(appointmentJpaEntity.getMaterialType()), // Assuming MaterialType is an enum
//                appointmentJpaEntity.getWindowStart(),
//                appointmentJpaEntity.getWindowEnd(),
//                new SellerId(appointmentJpaEntity.getSellerId()) // Assuming SellerId takes UUID
//        );
//    }
//
//}
