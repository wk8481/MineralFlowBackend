//package be.kdg.prgramming6.adapters.out;
//
//import be.kdg.prgramming6.domain.Appointment;
//import be.kdg.prgramming6.events.AppointmentUpdatedEvent;
//import be.kdg.prgramming6.port.out.UpdateAppointmentPort;
//import org.springframework.stereotype.Component;
//import org.springframework.context.ApplicationEventPublisher;
//
//@Component
//public class AppointmentUpdatedPublisher implements UpdateAppointmentPort {
//    private final AppointmentEventPublisher publisher;
//
//    @Override
//    public void updateAppointment(Appointment appointment) {
//        publisher.publishEvent(new AppointmentUpdatedEvent(
//                appointment.getSeller()
//        ));
//
//    }
//}
