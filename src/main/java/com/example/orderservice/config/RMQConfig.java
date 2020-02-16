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
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RMQConfig {

    @Value("${rabbitmq.dead_order.ttl}")
    private Long deadOrderTtl;

    @Value("${rabbitmq.routing_key.order}")
    private String orderRoutingKey;

    @Value("${rabbitmq.routing_key.dead_order}")
    private String deadOrderRoutingKey;

    @Value("${rabbitmq.queues.order}")
    private String orderQueue;

    @Value("${rabbitmq.exchanges.order}")
    private String orderExchange;

    @Value("${rabbitmq.queues.dead_order}")
    private String deadOrderQueue;

    @Value("${rabbitmq.exchanges.dead_order}")
    private String deadOrderExchange;

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

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", orderExchange);
        args.put("x-dead-letter-routing-key", orderQueue);
        args.put("x-message-ttl", deadOrderTtl);

        rabbitAdmin.declareQueue(new Queue(deadOrderQueue, true, false, false, args));
        rabbitAdmin.declareExchange(new DirectExchange(deadOrderExchange, true, false));
        rabbitAdmin.declareBinding(
                new Binding(deadOrderQueue, Binding.DestinationType.QUEUE, deadOrderExchange, deadOrderRoutingKey,
                            null));

        args = new HashMap<>();
        args.put("x-dead-letter-exchange", deadOrderExchange);
        args.put("x-dead-letter-routing-key",deadOrderRoutingKey);

        rabbitAdmin.declareQueue(new Queue(orderQueue, true, false, false, args));
        rabbitAdmin.declareExchange(new DirectExchange(orderExchange, true, false));
        rabbitAdmin.declareBinding(new Binding(orderQueue, Binding.DestinationType.QUEUE, orderExchange, orderRoutingKey, null));
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
