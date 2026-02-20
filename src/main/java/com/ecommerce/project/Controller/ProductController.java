package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductServices productServices;

    @PostMapping("/admin/categoryies/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product  product,
                                                 @PathVariable Long categoryId) {
    ProductDTO productDTO = productServices.addProduct(categoryId,product);
    return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }
}
