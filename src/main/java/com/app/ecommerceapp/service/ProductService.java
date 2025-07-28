package com.app.ecommerceapp.service;

import com.app.ecommerceapp.dto.ProductDto;
import com.app.ecommerceapp.model.Category;
import com.app.ecommerceapp.model.Product;
import com.app.ecommerceapp.repository.CategoryRepository;
import com.app.ecommerceapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDto newProductDto(){
        return new ProductDto();
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto).toList();
    }

    public Product addProduct(String name, String description, BigDecimal price, Integer quantity, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Kategoria o nazwie '" + categoryName + "' nie została znaleziona"));

        return createProduct(name, description, price, quantity, category);
    }

    public ProductDto findById(String id){
        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Produkt nie został znaleziony"));
    }

    //mapowanie na Product
    private Product createProduct(String name, String description, BigDecimal price, Integer quantity, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategory(category);
        return productRepository.save(product);
    }

    //mapowanie na ProductDto
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategoryName(product.getCategory().getName());
        return productDto;
    }

}
