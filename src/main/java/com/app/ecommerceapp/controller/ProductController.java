package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.dto.CustomerDto;
import com.app.ecommerceapp.dto.ProductDto;
import com.app.ecommerceapp.service.CategoryService;
import com.app.ecommerceapp.service.CustomerService;
import com.app.ecommerceapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CustomerService customerService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public String getAll(Model model) {
        UserDetails currentUser = customerService.getCurrentUser();
        if (currentUser != null) {
            CustomerDto customerDto = customerService.findByLogin(currentUser.getUsername());
            model.addAttribute("userDetails", customerDto);
        }
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String getProductDetails(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "productDetails";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        model.addAttribute("product", productService.newProductDto());
        model.addAttribute("categories", categoryService.getAll());
        return "addProduct";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute ProductDto createProductDto) {
         productService.addProduct(
                createProductDto.getName(),
                createProductDto.getDescription(),
                createProductDto.getPrice(),
                createProductDto.getQuantity(),
                createProductDto.getCategoryName()
        );
         return "redirect:products";
    }

}
