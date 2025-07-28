package com.app.ecommerceapp.service;

import com.app.ecommerceapp.model.Cart;
import com.app.ecommerceapp.model.CartProduct;
import com.app.ecommerceapp.model.Product;
import com.app.ecommerceapp.repository.CartProductRepository;
import com.app.ecommerceapp.repository.CartRepository;
import com.app.ecommerceapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    public Cart addProductToCart(String cartId, String productId, Integer quantity) {
        Cart cart = getCart(cartId);
        Product product = getProduct(productId);
        Optional<CartProduct> existingCartProduct = getExistingCartProduct(productId, cart);

        if (existingCartProduct.isPresent()) {
            incrementExistingCartProductQuantity(quantity, existingCartProduct.get());
        } else {
            addNewCartProduct(quantity, product, cart);
        }
        return cart;
    }

    private void addNewCartProduct(Integer quantity, Product product, Cart cart) {
        CartProduct cartProduct = createCartProduct(quantity, product, cart);
        cart.getCartProducts().add(cartProduct);
        cartRepository.save(cart);
    }

    private void incrementExistingCartProductQuantity(Integer quantity, CartProduct existingCartProduct) {
        existingCartProduct.setQuantity(existingCartProduct.getQuantity() + quantity);
        cartProductRepository.save(existingCartProduct);
    }

    private static Optional<CartProduct> getExistingCartProduct(String productId, Cart cart) {
        return cart.getCartProducts().stream()
                .filter(existingProduct -> existingProduct.getProduct().getId().equals(productId))
                .findFirst();
    }

    private static CartProduct createCartProduct(Integer quantity, Product product, Cart cart) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cartProduct.setCart(cart);
        return cartProduct;
    }


    private Cart getCart(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Koszyk nie został znaleziony"));
    }

    private Product getProduct(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
    }

    public void removeProductFromCart(String cartProductId) {
        cartProductRepository.deleteById(cartProductId);
    }

}
