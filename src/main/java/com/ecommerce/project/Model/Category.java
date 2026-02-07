package com.ecommerce.project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

     @Entity(name="Categories")
     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public class Category {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long categoryId;
     @NotBlank
     @Size(min = 5, message = "Size Must Be Atleast Of 5")
     private String categoryName;

}