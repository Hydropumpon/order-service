package com.example.orderservice.logging.messages;

public class CustomerControllerLogMessages {

    public static final String GET_ALL_CUSTOMERS = "All customers information requested";
    public static final String GET_ONE_CUSTOMER = "Customer information requested for: {}";
    public static final String ADD_NEW_CUSTOMER = "New customer creation requested: {}";
    public static final String UPDATE_CUSTOMER = "Customer update requested for id:{} and information: {}";
    public static final String DELETE_CUSTOMER = "Customer delete requested for: {}";

    private CustomerControllerLogMessages() {
    }
}

