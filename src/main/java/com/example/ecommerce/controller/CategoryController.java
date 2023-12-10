package com.example.ecommerce.controller;

import com.example.ecommerce.common.responses.ApiResponse;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(
                categoryService.getCategories(), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(
                new ApiResponse(true, "created a new category"), HttpStatus.CREATED
        );
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category) {
        try {
            category.setId(categoryId);
            categoryService.updateCategory(category);
            return new ResponseEntity<>(
                    new ApiResponse(true, "updated the category"), HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND
            );
        }
    }
}
