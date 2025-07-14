package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.model.Address;
import com.app.ecommerceapp.model.Customer;
import com.app.ecommerceapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class CustomerRegisterController {

    private final CustomerRepository customerRepository;

    @PostMapping()
    public Customer addCustomer() {
        Address address = new Address();
        address.setCity("Kraków");
        address.setStreet("Krakowska");
        address.setApartmentNumber("10");
        address.setZipCode("32-050");

        Customer customer = new Customer();
        customer.setName("Dawid");
        customer.setLastName("Tyrka");
        customer.setEmail("dawid10@gmail.com");
        customer.setPhoneNumber("123456789");
        customer.setAddress(address);

        return customerRepository.save(customer);
    }
}
