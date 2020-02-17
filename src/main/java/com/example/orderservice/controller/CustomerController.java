package com.example.orderservice.controller;

import com.example.orderservice.converter.ConverterDto;
import com.example.orderservice.dto.CustomerDto;
import com.example.orderservice.entity.Customer;
import com.example.orderservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/order-service/customer")
public class CustomerController {

    private CustomerService customerService;

    private ConverterDto<Customer, CustomerDto> customerConverterDto;

    @Autowired
    public CustomerController(CustomerService customerService,
                              ConverterDto<Customer, CustomerDto> customerConverterDto) {
        this.customerService = customerService;
        this.customerConverterDto = customerConverterDto;
    }

    @GetMapping
    public List<CustomerDto> getCustomers(Pageable pageable) {
        return customerConverterDto.toDto(customerService.getCustomers(pageable).getContent());
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable(name = "id") Integer id) {
        return customerConverterDto.toDto(customerService.getCustomer(id));
    }

    @PostMapping
    public CustomerDto addCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Customer customer = customerConverterDto.toEntity(customerDto);
        return customerConverterDto.toDto(customerService.addCustomer(customer));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Integer customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable("id") Integer id){
        Customer customer = customerConverterDto.toEntity(customerDto);
        return customerConverterDto.toDto(customerService.updateCustomer(customer, id));
    }

}
