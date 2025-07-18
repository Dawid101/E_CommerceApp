package com.app.ecommerceapp.service;

import com.app.ecommerceapp.dto.CategoryDto;
import com.app.ecommerceapp.model.Category;
import com.app.ecommerceapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setName(category.getName());
                    return categoryDto;
                }).toList();
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Kategoria o nazwie '" + name + "' nie została znaleziona"));
    }
}

