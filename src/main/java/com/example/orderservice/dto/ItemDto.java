package com.example.orderservice.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ItemDto {

    private final Integer id;

    private final String name;

    private final String description;

    private final BigDecimal price;

    @ConstructorProperties({"id", "name", "description", "price"})
    public ItemDto(Integer id, String name, String description,
                   BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
