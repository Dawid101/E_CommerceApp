package com.app.ecommerceapp.service;

import com.app.ecommerceapp.dto.CustomerDto;
import com.app.ecommerceapp.model.Customer;
import com.app.ecommerceapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public CustomerDto newDtoCustomer() {
        return new CustomerDto();
    }

    public void save(Customer customer) {
        var bCryptEncoder = new BCryptPasswordEncoder();
        customer.setPassword(bCryptEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByLogin(login);
        if(customer != null){
            var user = User.withUsername(customer.getLogin())
                    .password(customer.getPassword())
                    .build();
            return user;
        }
        return null;
    }
}
