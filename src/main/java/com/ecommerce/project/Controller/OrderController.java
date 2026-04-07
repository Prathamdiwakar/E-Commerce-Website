package com.ecommerce.project.Controller;

import com.ecommerce.project.Util.AuthUtils;
import com.ecommerce.project.payload.OrderDTO;
import com.ecommerce.project.payload.OrderRequestDTO;
import com.ecommerce.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/order/user/payment/{paymentMethod}")
    public ResponseEntity<OrderDTO> OrderProduct(@PathVariable String paymentMethod,
                                                 @RequestBody OrderRequestDTO requestDTO){
       String emailId = authUtils.loggedInEmail();
      OrderDTO orderDTO = orderService.placeOrder(
               emailId,
               requestDTO.getAddressId(),
               paymentMethod,
               requestDTO.getPgName(),
               requestDTO.getPgStatus(),
               requestDTO.getPgPaymentId(),
               requestDTO.getPgResponse()
       );
       return new ResponseEntity<>(orderDTO ,HttpStatus.CREATED);
    }
}
