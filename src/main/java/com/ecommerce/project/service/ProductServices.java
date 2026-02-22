package com.ecommerce.project.service;

import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductServices {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProduct();

    ProductResponse getProductbyId(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);

    ProductDTO updateProductByProductId(Long productId, ProductDTO productDTO);

    ProductDTO deleteProductById(Long productId);

    ProductDTO UpdateProductImage(Long productId, MultipartFile image);
}
