package com.app.ecommerceapp.service;

import com.app.ecommerceapp.model.*;
import com.app.ecommerceapp.repository.CartProductRepository;
import com.app.ecommerceapp.repository.CartRepository;
import com.app.ecommerceapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartProductRepository cartProductRepository;

    public List<Order> getOrdersByCustomerId(String id) {
        return orderRepository.findByCustomerId(id);
    }

    public void placeOrder(String cartId) {
        Cart cart = getCart(cartId);
        List<Product> products = getProducts(cart);
        Customer customer = cart.getCustomer();
        Order order = createOrder(products, customer);
        order.setTotalPrice(calculateTotalPrice(cart.getCartProducts()));
        orderRepository.save(order);
        clearCart(cartId);
    }

    private Order createOrder(List<Product> products, Customer customer) {
        Order order = new Order();
        order.setOrderDate(Instant.now());
        order.setOrderProducts(createOrderProducts(products, order));
        order.setCustomer(customer);
        return order;
    }

    private BigDecimal calculateTotalPrice(List<CartProduct> cartProducts) {
        return cartProducts.stream()
                .map(product -> product.getProduct().getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderProduct> createOrderProducts(List<Product> products, Order order) {
        return products.stream().map(product -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setName(product.getName());
                    orderProduct.setPrice(product.getPrice());
                    orderProduct.setQuantity(product.getQuantity());
                    orderProduct.setOrder(order);
                    return orderProduct;
                })
                .toList();
    }

    private List<Product> getProducts(Cart cart) {
        return cart.getCartProducts()
                .stream().map(CartProduct::getProduct)
                .toList();
    }

    private Cart getCart(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));
    }

    public void clearCart(String cartId) {
        cartProductRepository.deleteByCartId(cartId);
    }
}
