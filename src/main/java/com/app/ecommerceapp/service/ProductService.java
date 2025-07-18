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

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto).toList();
    }

    public Product addProduct(String name, String description, BigDecimal price, Integer quantity, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Kategoria o nazwie '" + categoryName + "' nie została znaleziona"));

        return createProduct(name, description, price, quantity, category);
    }

    public ProductDto findByName(String name) {
        return productRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Produkt o nazwie '" + name + "' nie został znaleziony"));
    }


    //pomocnicza metoda dodawnia produktu
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
    public ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategoryName(product.getCategory().getName());
        return productDto;
    }

}
