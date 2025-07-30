package com.app.ecommerceapp.service;

import com.app.ecommerceapp.model.*;
import com.app.ecommerceapp.repository.CartProductRepository;
import com.app.ecommerceapp.repository.CartRepository;
import com.app.ecommerceapp.repository.OrderRepository;
import com.app.ecommerceapp.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    public void reducingAmountOfProduct(List<CartProduct> cartProducts) {
        cartProducts.forEach(cartProduct -> {
            Product product = productRepository.findById(cartProduct.getProduct().getId()).orElseThrow();
            product.setQuantity(product.getQuantity() - cartProduct.getQuantity());
            productRepository.save(product);
        });
    }

    public List<Order> getOrdersByCustomerId(String id) {
        return orderRepository.findByCustomerId(id);
    }

    public void placeOrder(String cartId) {
        Cart cart = getCart(cartId);
        List<CartProduct> cartProducts = cart.getCartProducts();
        Customer customer = cart.getCustomer();
        Order order = createOrder(cartProducts, customer);
        order.setTotalPrice(calculateTotalPrice(cart.getCartProducts()));
        orderRepository.save(order);
        reducingAmountOfProduct(cart.getCartProducts());
        clearCart(cartId);
    }

    private Order createOrder(List<CartProduct> cartProducts, Customer customer) {
        Order order = new Order();
        order.setOrderDate(Instant.now());
        order.setOrderProducts(createOrderProducts(cartProducts, order));
        order.setCustomer(customer);
        return order;
    }

    private BigDecimal calculateTotalPrice(List<CartProduct> cartProducts) {
        return cartProducts.stream()
                .map(product -> product.getProduct().getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderProduct> createOrderProducts(List<CartProduct> cartProducts, Order order) {
        return cartProducts.stream().map(cartProduct -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setName(cartProduct.getProduct().getName());
                    orderProduct.setPrice(cartProduct.getProduct().getPrice());
                    orderProduct.setQuantity(cartProduct.getQuantity());
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

    public Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zamówienie nie zostało znalezione"));
    }
}
