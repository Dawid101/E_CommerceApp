package com.app.ecommerceapp.controller;

import com.app.ecommerceapp.dto.CategoryDto;
import com.app.ecommerceapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDto> getAll() {
        return categoryService.getAll();
    }
}
