package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.service.CategoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allcategories = categoryServices.getAllcategories();
        return new ResponseEntity<>(allcategories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryServices.createCategory(category);
        return new ResponseEntity<>("Category created Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
            String status = categoryServices.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable Long categoryId) {

            Category savedCategory = categoryServices.updateCategory(category,categoryId);
             return new ResponseEntity<>("UpdatedCategory With CategoryId: "+categoryId, HttpStatus.OK);
        }
    }





