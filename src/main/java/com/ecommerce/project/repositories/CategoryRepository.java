package com.ecommerce.project.repositories;

import com.ecommerce.project.Model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(@NotBlank @Size(min = 5, message = "Size Must Be Atleast Of 5") String categoryName);
}
