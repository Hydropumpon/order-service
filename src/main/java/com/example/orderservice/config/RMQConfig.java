package com.example.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RMQConfig {

    @Value("${rabbitmq.queues.order}")
    private String orderQueue;

    @Value("${rabbitmq.exchanges.order}")
    private String orderExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private SimpleRabbitListenerContainerFactory containerFactory;

    @Autowired
    private RabbitAdmin rabbitAdmin;


    @PostConstruct
    protected void init() {
        rabbitTemplate.setMessageConverter(messageConverter);
        containerFactory.setMessageConverter(messageConverter);
        containerFactory.setMissingQueuesFatal(false);
        declarePointToPointQueue();
    }

    private void declarePointToPointQueue() {
        Queue queue = new Queue(orderQueue);
        DirectExchange exchange = new DirectExchange(orderExchange);
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), "", null);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(binding);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper()
                                                        .registerModule(new JavaTimeModule())
                                                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                                                   true));
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(rabbitTemplate);
    }
}
