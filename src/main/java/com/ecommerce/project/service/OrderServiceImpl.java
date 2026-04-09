package com.ecommerce.project.service;

import com.ecommerce.project.Model.*;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.OrderDTO;
import com.ecommerce.project.payload.OrderItemDTO;
import com.ecommerce.project.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgStatus, String pgPaymentId, String pgResponse) {
        cart carts = cartRepository.findCartByEmail(emailId);
        if(carts==null){
            throw new ResourceNotFoundException("Cart","Email",emailId);
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));

        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(carts.getTotalPrice());
        order.setOrderStatus("Order Accepted !");
        order.setAddress(address);

        Payment payment = new Payment(paymentMethod,paymentMethod,pgStatus,pgPaymentId,pgResponse);
        payment.setOrder(order);
        payment =paymentRepository.save(payment);
        order.setPayment(payment);

        Order saveOrder = orderRepository.save(order);

        List<CartItem> cartItems= carts.getCartItems();
        if(cartItems.isEmpty()){
            throw new APIException("Cart is Empty");
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem: cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProducts());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductprice());
            orderItem.setOrder(saveOrder);
            orderItems.add(orderItem);
        }
        orderItems = orderItemRepository.saveAll(orderItems);

        //update the product Stock
        carts.getCartItems().forEach(item->{
            int quantity  = item.getQuantity();
            Product product = item.getProducts();
            product.setQuantity(product.getQuantity()-quantity);
            productRepository.save(product);

            //clear the cart
            cartService.deleteProductFromCart(carts.getCartId(),item.getProducts().getProductId());
        });

        OrderDTO orderDTO = modelMapper.map(saveOrder, OrderDTO.class);
        orderItems.forEach(orderItem -> {
            OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
            orderItemDTO.getProduct().setQuantity(orderItem.getQuantity());
            orderDTO.getOrderItems().add(orderItemDTO);
        });
        return orderDTO;
    }
}
