package com.example.orderservice.service.impl;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.OrderLineDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLine;
import com.example.orderservice.messages.OrderMessage;
import com.example.orderservice.service.RMQOrderProcessing;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RMQOrderProcessingImpl implements RMQOrderProcessing {

    @Value("${rabbitmq.exchanges.order}")
    private String orderExchange;

    @Autowired
    private ConverterDto<OrderLine, OrderLineDto> orderLineDtoConverter;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void processOrderMessage(OrderMessage orderMessage) {
        rabbitTemplate.convertAndSend(orderExchange, "", orderMessage);
    }

    @Override
    public void processOrderMessage(Order order, List<OrderLine> orderLineList) {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(order.getId());
        orderMessage.setState(order.getState());
        orderMessage.setOrderLineList(orderLineDtoConverter.toDto(orderLineList));

        this.processOrderMessage(orderMessage);
    }


}
