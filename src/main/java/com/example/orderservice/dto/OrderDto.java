package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@ToString
public class OrderDto {
    private final Integer id;

    @NotBlank
    private final Integer customerId;

    @Null
    private final String state;

    @Null
    private final LocalDate creationDate;

    @Null
    private final LocalDate checkDate;

    private final BigDecimal amount;

    @ConstructorProperties({"id", "customerId", "state", "creationDate", "checkDate", "amount"})
    public OrderDto(Integer id, @NotBlank Integer customerId, @NotNull String state, @Null LocalDate creationDate,
                    @Null LocalDate checkDate, BigDecimal amount) {
        this.id = id;
        this.customerId = customerId;
        this.state = state;
        this.creationDate = creationDate;
        this.checkDate = checkDate;
        this.amount = amount;
    }
}
