package com.app.ecommerceapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"customer", "cartProducts"})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(mappedBy = "cart")
    private Customer customer;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts = new ArrayList<>();

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", customer=" + customer.getId() +
                '}';
    }

}
