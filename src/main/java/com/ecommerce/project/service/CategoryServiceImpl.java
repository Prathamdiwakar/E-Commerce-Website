package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryServices {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with id %s not found", categoryId)));
        categoryRepository.delete(category);
        return "Category deleted successfully"+categoryId + "The Given Category is Deleted";
    }

    @Override
    public List<Category> getAllcategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
    categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category SavedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with id %s not found", categoryId)));
        category.setCategoryId(categoryId);
        SavedCategory= categoryRepository.save(category);
        return SavedCategory;
    }
}
