package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.model.Customer;
import com.app.ecommerceapp.model.Order;
import com.app.ecommerceapp.service.CustomerService;
import com.app.ecommerceapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;


    @GetMapping("/my-orders")
    public String getOrders(Authentication authentication, Model model) {
        Customer customer = customerService.findByUsername(authentication.getName());
        List<Order> orders = orderService.getOrdersByCustomerId(customer.getId());
        model.addAttribute("orders", orders);
        return "order";
    }

    @GetMapping("/add-order")
    public String placeOrder(Authentication authentication) {
        Customer customer = customerService.findByUsername(authentication.getName());
        String cartId = customer.getCart().getId();
        orderService.placeOrder(cartId);
        return "redirect:my-orders";
    }

    @GetMapping("/order-details/{id}")
    public String getOrderDetails(@PathVariable String id, Model model) {
        model.addAttribute("orderProduct", orderService.getOrder(id).getOrderProducts());
        return "orderDetails";
    }
}
