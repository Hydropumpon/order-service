package com.example.orderservice.converter.impl;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.OrderLineDto;
import com.example.orderservice.entity.OrderLine;
import org.springframework.stereotype.Component;

@Component
public class OrderLineConverterDto implements ConverterDto<OrderLine, OrderLineDto> {
    @Override
    public OrderLine toEntity(OrderLineDto dto) {
        return OrderLine.builder()
                        .id(dto.getId())
                        .itemId(dto.getItemId())
                        .quantity(dto.getQuantity())
                        .build();
    }

    @Override
    public OrderLineDto toDto(OrderLine entity) {
        return OrderLineDto.builder()
                           .id(entity.getId())
                           .itemId(entity.getItemId())
                           .quantity(entity.getQuantity())
                           .build();
    }
}
