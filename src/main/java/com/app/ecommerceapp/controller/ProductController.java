package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.dto.ProductDto;
import com.app.ecommerceapp.model.Product;
import com.app.ecommerceapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/{name}")
    public String getProductDetails(@PathVariable String name, Model model){
        model.addAttribute("product", productService.findByName(name));
        return "productDetails";
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDto createProductDto){
        return productService.addProduct(
                createProductDto.getName(),
                createProductDto.getDescription(),
                createProductDto.getPrice(),
                createProductDto.getQuantity(),
                createProductDto.getCategoryName()
        );
    }


}
