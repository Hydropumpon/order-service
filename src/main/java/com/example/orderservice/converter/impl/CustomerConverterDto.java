package com.example.orderservice.converter.impl;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.CustomerDto;
import com.example.orderservice.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverterDto implements ConverterDto<Customer, CustomerDto> {
    @Override
    public Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                       .email(dto.getEmail())
                       .id(dto.getId())
                       .phoneNumber(dto.getPhoneNumber())
                       .isDeleted(false)
                       .build();
    }

    @Override
    public CustomerDto toDto(Customer entity) {
        return CustomerDto.builder()
                          .email(entity.getEmail())
                          .id(entity.getId())
                          .phoneNumber(entity.getPhoneNumber())
                          .build();
    }
}
