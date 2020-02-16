package com.example.orderservice.service;

import com.example.orderservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Customer getCustomer(Integer id);

    Page<Customer> getCustomers(Pageable pageable);

    Customer addCustomer(Customer customer);

    void deleteCustomer(Integer customerId);

    Customer updateCustomer(Customer customer, Integer id);
}
