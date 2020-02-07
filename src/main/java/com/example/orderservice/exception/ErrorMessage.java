package com.example.orderservice.exception;

public abstract class ErrorMessage {

    public static final String CUSTOMER_NOT_EXIST = "Customer not exist";

    public static final String CUSTOMER_ALREADY_EXIST = "Customer already exist";

    public static final String ORDER_NOT_FOUND = "Order not found";

    private ErrorMessage() {
    }

}
