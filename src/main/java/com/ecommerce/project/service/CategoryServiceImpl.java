package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryServices {

    private List<Category> categories = new ArrayList<>();
    public long nextId = 1l;

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream().filter(c -> Objects.equals(c.getCategoryId(), categoryId))
                .findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with id %s not found", categoryId)));
        categories.remove(category);
        return "Category deleted successfully"+categoryId + "The Given Category is Deleted";
    }

    @Override
    public List<Category> getAllcategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
    category.setCategoryId(nextId++);
    categories.add(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> optinalCategory = categories.stream().filter(c -> Objects.equals(c.getCategoryId(), categoryId))
                .findFirst();

        if (optinalCategory.isPresent()) {
            Category updatedCategory = optinalCategory.get();
            updatedCategory.setCategoryName(category.getCategoryName());
            return updatedCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with id %s not found", categoryId));
        }
    }
}
