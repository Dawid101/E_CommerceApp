package com.app.ecommerceapp.service;

import com.app.ecommerceapp.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class SpringDataUserDetailsService implements UserDetailsService {

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {throw new UsernameNotFoundException(username); }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        customer.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getRoleName())));
        return new User(
                customer.getLogin(), customer.getPassword(), grantedAuthorities);
    }

}
