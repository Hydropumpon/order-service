package com.example.orderservice.controller;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.ItemDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderLineDto;
import com.example.orderservice.dto.wrapper.OrderDtoWrapper;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLine;
import com.example.orderservice.messages.OrderMessage;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.example.orderservice.logging.messages.OrderControllerLogMessages.*;

@RestController
@RequestMapping("/order-service/order")
@Slf4j
public class OrderController {

    private OrderService orderService;

    private ConverterDto<Order, OrderDto> orderConverterDto;

    private ConverterDto<OrderLine, OrderLineDto> orderLineConverterDto;

    @Autowired
    public OrderController(OrderService orderService,
                           ConverterDto<Order, OrderDto> orderConverterDto,
                           ConverterDto<OrderLine, OrderLineDto> orderLineConverterDto) {
        this.orderService = orderService;
        this.orderConverterDto = orderConverterDto;
        this.orderLineConverterDto = orderLineConverterDto;
    }


    @PostMapping
    public OrderDto addOrder(@RequestBody @Valid OrderDtoWrapper orderDtoWrapper) {
        log.info(ADD_ORDER, orderDtoWrapper);
        Order order = orderConverterDto.toEntity(orderDtoWrapper.getOrderDto());
        List<OrderLine> orderLineList = orderLineConverterDto.toEntity(orderDtoWrapper.getOrderLineDtoList());
        return orderConverterDto.toDto(orderService.addOrder(order, orderLineList));
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable("id") Integer id) {
        log.info(GET_ONE_ORDER, id);
        return orderConverterDto.toDto(orderService.getOrder(id));
    }

    @GetMapping
    public List<OrderDto> getOrders(Pageable pageable) {
        log.info(GET_ALL_ORDERS);
        return orderConverterDto.toDto(orderService.getOrders(pageable).getContent());
    }

    @GetMapping("/customer/{id}")
    public List<OrderDto> getOrdersByCustomer(@PathVariable("id") Integer id) {
        log.info(GET_ORDERS_BY_CUSTOMER, id);
        return orderConverterDto.toDto(orderService.getOrdersByCustomer(id));
    }

    @GetMapping("/{id}/composition")
    public List<ItemDto> getOrderComposition(@PathVariable("id") Integer id) {
        log.info(GET_ORDER_COMPOSITION, id);
        return orderService.getOrderComposition(id);
    }

    @PutMapping("/{id}")
    public void updateOrder(@RequestBody OrderMessage orderMessage, @PathVariable("id") Integer id) {
        log.info(UPDATE_ORDER, id, orderMessage);
        orderService.updateOrder(orderMessage, id);
    }
}
