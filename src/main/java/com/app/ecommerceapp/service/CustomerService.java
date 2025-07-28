package com.app.ecommerceapp.service;

import com.app.ecommerceapp.dto.CustomerDto;
import com.app.ecommerceapp.model.Cart;
import com.app.ecommerceapp.model.Customer;
import com.app.ecommerceapp.model.Role;
import com.app.ecommerceapp.repository.CartRepository;
import com.app.ecommerceapp.repository.CustomerRepository;
import com.app.ecommerceapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    public CustomerDto newDtoCustomer() {
        return new CustomerDto();
    }

    @Transactional
    public void save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        customer.setCart(cart);
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        customer.setRoles(new HashSet<>(Arrays.asList(userRole)));
        customerRepository.save(customer);

    }

    public CustomerDto findByLogin(String login) {
        return mapToDto(customerRepository.findByLogin(login));
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByLogin(username);
    }

    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    private CustomerDto mapToDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setLogin(customer.getLogin());
        customerDto.setEmail(customer.getEmail());
        customerDto.setAddress(customer.getAddress());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setRoles(customer.getRoles());
        return customerDto;
    }
}
