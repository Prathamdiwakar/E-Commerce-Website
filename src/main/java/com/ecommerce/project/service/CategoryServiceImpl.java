package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryServices {


    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->  new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepository.delete(category);
        return "Category deleted successfully"+categoryId + "The Given Category is Deleted";
    }

    @Override
    public List<Category> getAllcategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("Category List is Empty !!!");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
    Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
    if (savedCategory != null) {
        throw new APIException("Category with  CategoryName :"  + category.getCategoryName() + " already exists !!!");
    }
    categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category SavedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryId(categoryId);
        SavedCategory= categoryRepository.save(category);
        return SavedCategory;
    }
}
