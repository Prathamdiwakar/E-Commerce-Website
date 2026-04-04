package com.ecommerce.project.service;

import com.ecommerce.project.Model.CartItem;
import com.ecommerce.project.Model.Product;
import com.ecommerce.project.Model.cart;
import com.ecommerce.project.Util.AuthUtils;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    AuthUtils authUtil;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        cart carts = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productIs", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(
                carts.getCartId(),
                productId
        );
        if (cartItem != null) {
            throw new APIException("Product " + product.getProductName() + "already exist in cart");
        }
        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + "is not available");
        }
        if (product.getQuantity() < quantity) {
            throw new APIException("Please, Make an order of the" + product.getProductName() +
                    "Less than or equal to the Quantity" + product.getQuantity() + ".");
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setProducts(product);
        newCartItem.setCarts(carts);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductprice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());
        carts.setTotalPrice(carts.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(carts);

        CartDTO cartDTO = modelMapper.map(carts, CartDTO.class);

        List<CartItem> cartItems = carts.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProducts(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });
        cartDTO.setProducts(productStream.toList());
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCart() {
        List<cart> carts = cartRepository.findAll();

        if (carts.size() == 0) {
            throw new APIException("No Carts Exist");
        }

        List<CartDTO> cartDTOS = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            List<ProductDTO> products = cart.getCartItems()
                    .stream().map(p -> modelMapper.map(p.getProducts(), ProductDTO.class))
                    .collect(Collectors.toList());
            cartDTO.setProducts(products);
            return cartDTO;
        }).collect(Collectors.toList());
        return cartDTOS;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        cart carts = cartRepository.findCartByEmailAndCartId(emailId, cartId);
        if (carts == null) {
            throw new ResourceNotFoundException("cartId", "cartId", cartId);
        }
        CartDTO cartDTO = modelMapper.map(carts, CartDTO.class);
        carts.getCartItems().forEach(c -> c.getProducts().setQuantity(c.getQuantity()));
        List<ProductDTO> products = carts.getCartItems().stream().map(
                        p -> modelMapper.map(p.getProducts(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Override
    @Transactional // its done the whole process or nothing it's done
    public CartDTO updateProductQuantityInCart(Long productId, int quantity) {
        String emailId = authUtil.loggedInEmail();
        cart carts = cartRepository.findCartByEmail(emailId);
        Long cartId = carts.getCartId();

        cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productIs", productId));

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + "is not available");
        }
        if (product.getQuantity() < quantity) {
            throw new APIException("Please, Make an order of the" + product.getProductName() +
                    "Less than or equal to the Quantity" + product.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
        if (cartItem == null) {
            throw new APIException("Product" + product.getProduct() + "not available in cart");
        }
        int netQuantity = cartItem.getQuantity() + quantity;
        if (netQuantity < 0) {
            throw new APIException("The Resulting Quantity Cannot be Negative");
        }
        if (netQuantity == 0) {
            deleteProductFromCart(cartId, productId);
        } else {
            cartItem.setProductprice(product.getSpecialPrice());
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(carts.getTotalPrice() + (cartItem.getProductprice() * quantity));
            cartRepository.save(cart);
        }
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        if (updatedCartItem.getQuantity() == 0) {
            cartItemRepository.deleteById(updatedCartItem.getCartItemId());
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProducts(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });
        cartDTO.setProducts(productStream.toList());
        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductprice() * cartItem.getQuantity()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProducts().getProductName() + " removed from the cart !!!";
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productIs", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId,productId);

        if(cartItem == null){
            throw new APIException("Product " + product.getProductName() +" not Available in the cart!");
        }

        double cartPrice = cart.getTotalPrice()-(cartItem
                .getProductprice()*cartItem.getQuantity());

        cartItem.setProductprice(product.getSpecialPrice());
        cart.setTotalPrice(cartPrice+(cartItem
                .getProductprice()*cartItem.getQuantity()));

        cartItem = cartItemRepository.save(cartItem);

    }

    private cart createCart() {
        cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null) {
            return userCart;
        }
        cart carts = new cart();
        carts.setTotalPrice(0.00);
        carts.setUser(authUtil.loggedInUser());
        cart savedCart = cartRepository.save(carts);
        return savedCart;
    }
}
