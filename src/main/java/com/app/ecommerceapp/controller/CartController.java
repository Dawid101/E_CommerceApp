package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.model.CartProduct;
import com.app.ecommerceapp.model.Customer;
import com.app.ecommerceapp.service.CartService;
import com.app.ecommerceapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.SortedMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;


    @PostMapping("/addToCart")
    public String addProductToCart(@RequestParam("productId") String productId,
                                   @RequestParam("quantity") Integer quantity,
                                   Authentication authentication) {
        Customer customer = customerService.findByUsername(authentication.getName());
        String cartId = customer.getCart().getId();
        cartService.addProductToCart(cartId, productId, quantity);
        return "redirect:cart";
    }

    @GetMapping("/cart")
    public String showCustomerCart(Model model, Authentication authentication) {
        Customer customer = customerService.findByUsername(authentication.getName());
        List<CartProduct> cartProducts = customer.getCart().getCartProducts();
        model.addAttribute("cartProducts", cartProducts);
        return "cart";
    }

    @DeleteMapping("/cart/{id}")
    public String removeCartProductFromCart(@PathVariable("id") String cartProductId){
        cartService.removeProductFromCart(cartProductId);
        return "redirect:/cart";
    }


}
