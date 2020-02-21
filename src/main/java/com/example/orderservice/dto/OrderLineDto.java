package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Positive;
import java.beans.ConstructorProperties;

@Getter
@Builder
@ToString
public class OrderLineDto {

    private final Integer id;

    @Positive
    private final Integer itemId;

    @Positive
    private final Integer quantity;

    @ConstructorProperties({"id", "itemId", "quantity"})
    public OrderLineDto(Integer id, Integer itemId, Integer quantity) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
