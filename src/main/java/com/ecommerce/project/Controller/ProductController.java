package com.ecommerce.project.Controller;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductServices productServices;

    @PostMapping("/admin/categoryies/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO  productDTO,
                                                 @PathVariable Long categoryId) {
    ProductDTO savedProductDTO = productServices.addProduct(categoryId,productDTO);
    return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
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

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,
                                                        @Valid @RequestBody ProductDTO productDTO) {
      ProductDTO savedProductDTO = productServices.updateProductByProductId(productId, productDTO);
      return new ResponseEntity<>( savedProductDTO, HttpStatus.FOUND);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO = productServices.deleteProductById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("product/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO productDTO = productServices.UpdateProductImage(productId,image);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

}
