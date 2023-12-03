package com.example.ecommerce.service;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.respository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    public void createCategory(Category category) {
        categoryRepo.save(category);
    }

    public void updateCategory(Category category) throws Exception {
        int id = category.getId();
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new Exception("Category with id = " + id + " does not exist.");
        }
        Category categoryToBeUpdated = categoryOptional.get();
        // TODO: Update these only based on the fields provided and not all the fields.
        categoryToBeUpdated.setCategoryName(category.getCategoryName());
        categoryToBeUpdated.setDescription(category.getDescription());
        categoryToBeUpdated.setImageUrl(category.getImageUrl());
        categoryRepo.save(categoryToBeUpdated);
    }
}
