package com.ecommerce.project.service;

import com.ecommerce.project.payload.OrderDTO;


public interface OrderService {
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgStatus, String pgPaymentId, String pgResponse);
}
