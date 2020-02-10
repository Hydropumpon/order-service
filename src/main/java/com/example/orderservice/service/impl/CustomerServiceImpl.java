package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Customer;
import com.example.orderservice.exception.ConflictException;
import com.example.orderservice.exception.ErrorMessage;
import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.exception.ServiceErrorCode;
import com.example.orderservice.repository.CustomerRepository;
import com.example.orderservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomer(Integer id) {
        return customerRepository.findById(id)
                                 .orElseThrow(() -> new NotFoundException(ErrorMessage.CUSTOMER_NOT_EXIST,
                                                                          ServiceErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public Customer addCustomer(Customer customer) {
        customerRepository.findByEmail(customer.getEmail())
                          .ifPresent(customerDb ->
                                     {
                                         throw new ConflictException(
                                                 ErrorMessage.CUSTOMER_ALREADY_EXIST,
                                                 ServiceErrorCode.ALREADY_EXIST);
                                     });
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepository.findById(id).ifPresent(customer -> customerRepository.delete(customer));
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
        checkCustomerDublicate(customer);
        return customerRepository.save(customer);
    }

    private void checkCustomerDublicate(Customer customer) {
        customerRepository.findByEmail(customer.getEmail())
                          .filter(customerDb -> !customerDb.getId().equals(customer.getId()))
                          .ifPresent(cust -> {
                              throw new ConflictException(ErrorMessage.CUSTOMER_ALREADY_EXIST,
                                                          ServiceErrorCode.ALREADY_EXIST);
                          });
    }


}
