package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.model.Role;
import com.app.ecommerceapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @PostMapping
    public void add(@RequestBody Role role){
        roleRepository.save(role);
    }

}
