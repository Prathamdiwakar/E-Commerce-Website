package com.ecommerce.project.service;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryServices {
     CategoryDTO deleteCategory(Long categoryId);


     CategoryDTO createCategory(CategoryDTO categoryDTO);

     CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

     CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
}
