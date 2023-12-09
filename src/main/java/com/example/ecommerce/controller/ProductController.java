package com.example.ecommerce.controller;

import com.example.ecommerce.common.responses.ApiResponse;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.respository.CategoryRepo;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> category = categoryRepo.findById(productDto.getCategoryId());

        if (category.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.BAD_REQUEST);
        }

        productService.createProduct(productDto, category.get());

        return new ResponseEntity<>(new ApiResponse(true, "product added"), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductDto productDto) {
        productDto.setId(productId);

        // Check if product category has to be updated.
        Category categoryToUpdate = null;
        if (productDto.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepo.findById(productDto.getCategoryId());
            if (categoryOptional.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(false, "the new category does not exist"), HttpStatus.BAD_REQUEST);
            }
            categoryToUpdate = categoryOptional.get();
        }

        try {
            productService.updateProduct(productDto, categoryToUpdate);
            return new ResponseEntity<>(new ApiResponse(true, "product updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
