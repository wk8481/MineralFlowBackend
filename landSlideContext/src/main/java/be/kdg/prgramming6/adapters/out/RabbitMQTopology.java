package be.kdg.prgramming6.adapters.out;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

public class RabbitMQTopology {

    public static final String LANDSLIDE_EVENTS_EXCHANGE = "landslide_events";
    public static final String APPOINTMENT_UPDATED_QUEUE = "appointment_updated";

    @Bean
    TopicExchange landslideEventsExchange() {
        return new TopicExchange(LANDSLIDE_EVENTS_EXCHANGE);
    }

    @Bean
    Queue appointmentReceivedQueue() {
        return new Queue(APPOINTMENT_UPDATED_QUEUE, true);
    }

    @Bean
    Binding binding(TopicExchange exchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("landslide.#.appointment.updated");
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
