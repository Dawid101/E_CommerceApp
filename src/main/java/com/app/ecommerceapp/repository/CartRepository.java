package com.app.ecommerceapp.repository;

import com.app.ecommerceapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
}
