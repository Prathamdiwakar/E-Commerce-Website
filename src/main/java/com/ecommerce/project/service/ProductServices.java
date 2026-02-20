package com.ecommerce.project.service;

import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.ProductDTO;

public interface ProductServices {
    ProductDTO addProduct(Long categoryId, Product product);
}
