package com.example.orderservice.converter.impl;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderConverterDto implements ConverterDto<Order, OrderDto> {

    static final private String ORDER_STATE = "CREATED";
    private CustomerService customerService;

    @Autowired
    public OrderConverterDto(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        return Order.builder()
                    .amount(dto.getAmount())
                    .checkDate(dto.getCheckDate())
                    .creationDate(LocalDate.now())
                    .customer(customerService.getCustomer(dto.getCustomerId()))
                    .id(dto.getId())
                    .state(ORDER_STATE)
                    .build();
    }

    @Override
    public OrderDto toDto(Order entity) {
        return OrderDto.builder()
                       .amount(entity.getAmount())
                       .checkDate(entity.getCheckDate())
                       .creationDate(entity.getCreationDate())
                       .customerId(entity.getCustomer().getId())
                       .id(entity.getId())
                       .state(entity.getState())
                       .build();
    }
}
