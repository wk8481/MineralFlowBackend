package be.kdg.prgramming6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingTopology {

    // Warehouse configuration
    public static final String WAREHOUSE_EVENTS_EXCHANGE = "warehouse_events";
    public static final String WAREHOUSE_ACTIVITY_CREATED_QUEUE = "warehouse_activity_created";

    @Bean
    TopicExchange warehouseEventsExchange() {
        return new TopicExchange(WAREHOUSE_EVENTS_EXCHANGE);
    }

    @Bean
    Queue warehouseActivityCreatedQueue() {
        return new Queue(WAREHOUSE_ACTIVITY_CREATED_QUEUE, true);
    }

    @Bean
    Binding warehouseBinding(TopicExchange warehouseEventsExchange, Queue warehouseActivityCreatedQueue) {
        return BindingBuilder
                .bind(warehouseActivityCreatedQueue)
                .to(warehouseEventsExchange)
                .with("warehouse.#.activity.created");
    }

    // Landside configuration
    public static final String LANDSIDE_EVENTS_EXCHANGE = "landside_events";
    public static final String APPOINTMENT_UPDATED_QUEUE = "appointment_updated";

    @Bean
    TopicExchange landSideEventsExchange() {
        return new TopicExchange(LANDSIDE_EVENTS_EXCHANGE);
    }

    @Bean
    Queue appointmentUpdatedQueue() {
        return new Queue(APPOINTMENT_UPDATED_QUEUE, true);
    }

    @Bean
    Binding landsideBinding(TopicExchange landSideEventsExchange, Queue appointmentUpdatedQueue) {
        return BindingBuilder
                .bind(appointmentUpdatedQueue)
                .to(landSideEventsExchange)
                .with("landside.#.appointment.updated");
    }

    // Common configuration
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}