package com.app.ecommerceapp.dto;

import com.app.ecommerceapp.model.Address;
import com.app.ecommerceapp.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class CustomerDto {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String login;
    private String password;
    private Address address;
    private Set<Role> roles;
}
