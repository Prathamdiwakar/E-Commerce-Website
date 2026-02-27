package com.ecommerce.project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min = 5, message = "Product Name Must be Of AtLeast Of 5 Words ")
    private String productName;
    private String image;
    @NotBlank
    @Size(min = 5, message = "Description Must be Of AtLeast Of 5 Words ")
    private String description;
    private Integer quantity;
    private Double price;
    private double discount;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
