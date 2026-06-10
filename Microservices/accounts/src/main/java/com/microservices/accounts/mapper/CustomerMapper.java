package com.microservices.accounts.mapper;

import com.microservices.accounts.dto.CustomerDTO;
import com.microservices.accounts.model.Customer;

import java.util.Optional;

public class CustomerMapper {

    public static CustomerDTO mapToCustomerDto(Optional<Customer> customer, CustomerDTO customerDto) {
        if(customer.isPresent()){
            customerDto.setName(customer.get().getName());
            customerDto.setEmail(customer.get().getEmail());
            customerDto.setMobileNumber(customer.get().getMobileNumber());
        }
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDTO customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

}
