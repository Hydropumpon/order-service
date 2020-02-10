package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Customer;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLine;
import com.example.orderservice.exception.ErrorMessage;
import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.exception.ServiceErrorCode;
import com.example.orderservice.messages.OrderMessage;
import com.example.orderservice.repository.OrderLineRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.CustomerService;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.RMQOrderProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private OrderLineRepository orderLineRepository;

    private RMQOrderProcessing rmqOrderProcessing;

    private CustomerService customerService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderLineRepository orderLineRepository,
                            RMQOrderProcessing rmqOrderProcessing,
                            CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.rmqOrderProcessing = rmqOrderProcessing;
        this.customerService = customerService;
    }


    @Override
    @Transactional
    public Order addOrder(Order order, List<OrderLine> orderLineList) {
        Order orderDb = orderRepository.save(order);
        orderLineList.forEach(orderLine -> orderLine.setOrder(orderDb));
        orderLineRepository.saveAll(orderLineList);

        rmqOrderProcessing.processOrderMessage(order, orderLineList);

        return orderDb;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Integer id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ORDER_NOT_FOUND, ServiceErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomer(Integer id) {
        Customer customer = customerService.getCustomer(id);
        return orderRepository.findAllByCustomer(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void updateOrder(OrderMessage orderMessage) {
        orderRepository
                .updateOrder(orderMessage.getId(), orderMessage.getAmount(), orderMessage.getApproveDate(),
                             orderMessage.getState());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderLine> getOrderComposition(Integer id) {
        return orderRepository.findById(id)
                              .map(order -> orderLineRepository.findByOrder(order))
                              .orElse(new ArrayList<>());
    }


}
