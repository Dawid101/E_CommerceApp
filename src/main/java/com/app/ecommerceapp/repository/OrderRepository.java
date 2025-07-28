package com.app.ecommerceapp.repository;

import com.app.ecommerceapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomerId(String id);
}
