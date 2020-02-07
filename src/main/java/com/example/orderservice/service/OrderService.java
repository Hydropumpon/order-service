package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLine;
import com.example.orderservice.messages.OrderMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order, List<OrderLine> orderLineList);

    Order getOrder(Integer id);

    List<Order> getOrdersByCustomer(Integer id);

    Page<Order> getOrders(Pageable pageable);

    void updateOrder(OrderMessage orderMessage);
}
