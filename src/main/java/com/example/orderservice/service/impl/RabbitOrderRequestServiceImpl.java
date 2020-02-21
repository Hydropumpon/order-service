package com.example.orderservice.service.impl;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.OrderLineDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLine;
import com.example.orderservice.messages.OrderMessage;
import com.example.orderservice.service.RabbitOrderRequestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitOrderRequestServiceImpl implements RabbitOrderRequestService {

    @Value("${rabbitmq.exchanges.order_request}")
    private String orderRequestExchange;

    @Value("${rabbitmq.routing_key.order_request}")
    private String orderRequestRoutingKey;

    private ConverterDto<OrderLine, OrderLineDto> orderLineDtoConverter;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitOrderRequestServiceImpl(
            ConverterDto<OrderLine, OrderLineDto> orderLineDtoConverter,
            RabbitTemplate rabbitTemplate) {
        this.orderLineDtoConverter = orderLineDtoConverter;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void processOrderMessage(OrderMessage orderMessage) {
        rabbitTemplate.convertAndSend(orderRequestExchange, orderRequestRoutingKey, orderMessage);
    }

    @Override
    public void processOrderMessage(Order order, List<OrderLine> orderLineList) {
        OrderMessage orderMessage = getOrderMessage(order);
        orderMessage.setOrderLineList(orderLineDtoConverter.toDto(orderLineList));
        this.processOrderMessage(orderMessage);
    }

    private OrderMessage getOrderMessage(Order order) {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(order.getId());
        orderMessage.setState(order.getState());
        return orderMessage;
    }


}
