package com.app.ecommerceapp.repository;

import com.app.ecommerceapp.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CartProductRepository extends JpaRepository<CartProduct, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CartProduct cp WHERE cp.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") String cartId);
}
