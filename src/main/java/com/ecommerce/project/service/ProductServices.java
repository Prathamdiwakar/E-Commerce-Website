package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductServices {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProduct(Integer pageSize, Integer pageNumber, String sortBy, String sortOrder);

    ProductResponse getProductbyId(Long categoryId, Integer pageSize, Integer pageNumber, String sortBy, String sortOrder);

    ProductResponse getProductByKeyword(String keyword, Integer pageSize, Integer pageNumber, String sortBy, String sortOrder);

    ProductDTO updateProductByProductId(Long productId, ProductDTO productDTO);

    ProductDTO deleteProductById(Long productId);

    ProductDTO UpdateProductImage(Long productId, MultipartFile image) throws IOException;
}
