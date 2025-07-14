package com.app.ecommerceapp.repository;

import com.app.ecommerceapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
