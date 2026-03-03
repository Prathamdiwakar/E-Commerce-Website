package com.ecommerce.project.Controller;

import com.ecommerce.project.config.AppConstants;
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
    public ResponseEntity<ProductResponse> getProduct(
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR ,required = false) String sortOrder
    ) {
        ProductResponse productResponse = productServices.getAllProduct(pageSize,pageNumber,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categoryies/{categoryId}/product")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long categoryId,
                                                          @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
                                                          @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
                                                          @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR ,required = false) String sortOrder) {
       ProductResponse productResponse = productServices.getProductbyId(categoryId,pageSize,pageNumber,sortBy,sortOrder);
       return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword,
                                                               @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
                                                               @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
                                                               @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                               @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR ,required = false) String sortOrder) {
        ProductResponse productResponse = productServices.getProductByKeyword(keyword,pageSize,pageNumber,sortBy,sortOrder);
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
