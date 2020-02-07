package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLine;
import com.example.orderservice.messages.OrderMessage;

import java.util.List;

public interface RMQOrderProcessing {

    void processOrderMessage(OrderMessage orderMessage);

    void processOrderMessage(Order order, List<OrderLine> orderLineList);
}
