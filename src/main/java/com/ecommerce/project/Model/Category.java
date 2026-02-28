package com.ecommerce.project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name="Categories")
     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public class Category {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long categoryId;
     @NotBlank
     @Size(min = 5, message = "Size Must Be AtLeast Of 5")
     private String categoryName;

      @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
      private List<Product> products;

}