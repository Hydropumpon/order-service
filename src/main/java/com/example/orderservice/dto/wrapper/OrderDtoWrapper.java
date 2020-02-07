package com.example.orderservice.dto.wrapper;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderLineDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoWrapper {

    @NotNull
    private OrderDto orderDto;

    @NotNull
    private List<OrderLineDto> orderLineDtoList;

}
