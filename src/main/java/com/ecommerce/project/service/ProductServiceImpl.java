package com.ecommerce.project.service;

import com.ecommerce.project.Model.Category;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Model.cart;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductServices {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        boolean isProductNotPresent = true;

        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (product.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.jpg");
            product.setCategory(category);
            double specialPrice = product.getPrice() -
                    ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else{
            throw new APIException("Product not found");
        }
    }

    @Override
    public ProductResponse getAllProduct(Integer pageSize, Integer pageNumber, String sortBy, String sortOrder) {
       Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                 Sort.by(sortBy).ascending()
               : Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
       Page<Product> productPage = productRepository.findAll(pageable);
     List<Product> allProducts = productPage.getContent();
     List<ProductDTO> productDTOS = allProducts.stream()
             .map(product -> modelMapper.map(product, ProductDTO.class))
             .toList();
     ProductResponse productResponse = new ProductResponse();
     productResponse.setContent(productDTOS);
     productResponse.setPageNumber(productPage.getNumber());
     productResponse.setPageSize(productPage.getSize());
     productResponse.setTotalPages(productPage.getTotalPages());
     productResponse.setTotalElements(productPage.getTotalElements());
     productResponse.setLastPage(productPage.isLast());
     return productResponse;
    }

    @Override
    public ProductResponse getProductbyId(Long categoryId, Integer pageSize, Integer pageNumber, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category,pageable);
        List<Product> allProducts = productPage.getContent();
        List<ProductDTO> productDTOS = allProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword, Integer pageSize, Integer pageNumber, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%',pageable);
        List<Product> allProducts = productPage.getContent();
        List<ProductDTO> productDTOS = allProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProductByProductId(Long productId, ProductDTO productDTO) {
        Product products = productRepository.findById(productId)
               .orElseThrow(()->new ResourceNotFoundException("Product","productId", productId));

        Product product = modelMapper.map(productDTO, Product.class);
        products.setProductName(product.getProductName());
        products.setDescription(product.getDescription());
        products.setQuantity(product.getQuantity());
        products.setPrice(product.getPrice());
        products.setDiscount(product.getDiscount());
        products.setSpecialPrice(product.getSpecialPrice());

        Product updatedProduct = productRepository.save(products);

        List<cart> carts = cartRepository.findCartsByProductId(productId);
        List<CartDTO> cartDTOs = carts.stream().map(cart->{
               CartDTO cartDTO = modelMapper.map(cart ,CartDTO.class);
               List<ProductDTO> productDTOS = cart.getCartItems().stream().map(
                       p->modelMapper.map(p.getProducts(), ProductDTO.class))
                       .toList();
               cartDTO.setProducts(productDTOS);
               return cartDTO;
    }).toList();

        cartDTOs.forEach(cartDTO -> cartService.updateProductInCarts(cartDTO.getCartId(),productId));

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductById(Long productId) {
        Product findProduct = productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product","productId" ,productId));
        List<cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(),productId));

        productRepository.delete(findProduct);
        return modelMapper.map(findProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO UpdateProductImage(Long productId, MultipartFile image) throws IOException {

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


        String fileName = fileService.uploadImage(path, image);

        productFromDb.setImage(fileName);

        Product updatedProduct = productRepository.save(productFromDb);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

}
