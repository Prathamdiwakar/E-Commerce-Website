package com.ecommerce.project.service;

import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductServices {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProduct();

    ProductResponse getProductbyId(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);
}
