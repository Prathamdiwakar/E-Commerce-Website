package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;

import java.util.List;

public interface CategoryServices {
     String deleteCategory(Long categoryId);

     List<Category> getAllcategories();
     void createCategory(Category category);

     Category updateCategory(Category category, Long categoryId);
}
