package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
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

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getProduct() {
        ProductResponse productResponse = productServices.getAllProduct();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categoryies/{categoryId}/product")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long categoryId) {
       ProductResponse productResponse = productServices.getProductbyId(categoryId);
       return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productServices.getProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
}
