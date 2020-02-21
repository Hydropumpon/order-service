package com.example.orderservice.logging.messages;

public class OrderControllerLogMessages {

    public static final String ADD_ORDER = "New order creation requested: {}";
    public static final String UPDATE_ORDER = "Order update requested for id: {} with params: {}";
    public static final String GET_ALL_ORDERS = "All orders information requested";
    public static final String GET_ONE_ORDER = "Order information requested for: {}";
    public static final String GET_ORDERS_BY_CUSTOMER = "Orders information requested for customer: {}";
    public static final String GET_ORDER_COMPOSITION = "Order's composition requested for order: {}";

    private OrderControllerLogMessages() {
    }
}


