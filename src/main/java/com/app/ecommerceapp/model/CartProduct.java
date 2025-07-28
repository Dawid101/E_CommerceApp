package com.app.ecommerceapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts_products")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;

    @Override
    public String toString() {
        return "CartProduct{" +
                "id='" + id + '\'' +
                ", cart=" + cart.getId() +
                ", product=" + product.getId() +
                ", quantity=" + quantity +
                '}';
    }
}
