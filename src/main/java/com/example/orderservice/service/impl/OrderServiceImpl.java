package com.example.orderservice.service.impl;

import com.example.orderservice.client.CatalogueClient;
import com.example.orderservice.dto.ItemDto;
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
import com.example.orderservice.service.RabbitOrderRequestService;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private CatalogueClient catalogueClient;

    private OrderRepository orderRepository;

    private OrderLineRepository orderLineRepository;

    private RabbitOrderRequestService rabbitOrderRequestService;

    private CustomerService customerService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderLineRepository orderLineRepository,
                            RabbitOrderRequestService rabbitOrderRequestService,
                            CustomerService customerService,
                            CatalogueClient catalogueClient) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.rabbitOrderRequestService = rabbitOrderRequestService;
        this.customerService = customerService;
        this.catalogueClient = catalogueClient;
    }


    @Override
    @Transactional
    public Order addOrder(Order order, List<OrderLine> orderLineList) {
        Order orderDb = orderRepository.save(order);
        orderLineList.forEach(orderLine -> orderLine.setOrder(orderDb));
        orderLineRepository.saveAll(orderLineList);
        rabbitOrderRequestService.processOrderMessage(order, orderLineList);
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
    public void updateOrder(OrderMessage orderMessage, Integer id) {
        orderRepository.findById(id)
                       .map(order -> {
                           order.setAmount(orderMessage.getAmount());
                           order.setCheckDate(orderMessage.getApproveDate());
                           order.setState(orderMessage.getState());
                           return orderRepository.save(order);
                       }).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ORDER_NOT_FOUND, ServiceErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getOrderComposition(Integer id) {
        return orderRepository.findById(id)
                              .map(order -> orderLineRepository.findByOrder(order))
                              .map(orderLines -> orderLines.stream()
                                                           .map(orderLine -> catalogueClient
                                                                   .getItemById(orderLine.getItemId()))
                                                           .collect(Collectors.toList())
                                  )
                              .orElseThrow(() -> new NotFoundException(ErrorMessage.ORDER_NOT_FOUND,
                                                                       ServiceErrorCode.NOT_FOUND));
    }


}
