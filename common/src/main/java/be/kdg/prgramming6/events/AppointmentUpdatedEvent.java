package be.kdg.prgramming6.events;

import be.kdg.prgramming6.domain.PersonId;

public record AppointmentUpdatedEvent (PersonId seller){
}
