package com.example.yeon.ecommercejava.controller;

import com.example.yeon.ecommercejava.dto.*;
import com.example.yeon.ecommercejava.services.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    public static final Logger orderLogger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
//    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
//        this.modelMapper = modelMapper;
    }

    @ResponseBody
    @PostMapping(path = "/api/makeOrder")
    public ResponseEntity<OrderMessage> makeOrder(@RequestBody MakeOrderRequestDTO orderRequestDTO) {

        try {
            // Check if Seller's Inventory has enough quantity
            List<CartInterface> cartList = orderRequestDTO.getOrderList();
            List<String> notEnoughQuantityMessage = new ArrayList<>();
            for(CartInterface cart : cartList) {
                Long inventoryId = cart.getId();
                Integer requestedQuantity = cart.getQuantity();
                if ( orderService.checkIfInventoryQuantityEnough(inventoryId,requestedQuantity) == "true"){
                    orderLogger.info("Make Order Successfully");
                }
                else {
                    notEnoughQuantityMessage.add(orderService.checkIfInventoryQuantityEnough(inventoryId,requestedQuantity) );
                }
            }
            if (notEnoughQuantityMessage.size() > 0 ) {
                return new ResponseEntity<>(new OrderMessage(String.join(", ",notEnoughQuantityMessage)), HttpStatus.BAD_REQUEST);
            }
            else {
                // Create Order
                for(CartInterface cart : cartList) {
                    Long inventoryId = cart.getId();
                    Integer requestedQuantity = cart.getQuantity();
                    String selectedColor = cart.getSelectedColor();
                    Long buyerId = orderRequestDTO.getUser();
                    if (orderService.makeOrder(inventoryId,requestedQuantity,selectedColor,buyerId) == false) {
                        return new ResponseEntity<>(new OrderMessage("Error in MakeOrder with Quantity issue"), HttpStatus.BAD_REQUEST);
                    }
                }
                // Successfully created order
                return new ResponseEntity<>(new OrderMessage("Order Created Successfully"), HttpStatus.CREATED);
            }
        }
        catch (Exception e) {
            orderLogger.info(String.valueOf(e));
            return new ResponseEntity<>(new OrderMessage("Error in MakeOrder"), HttpStatus.BAD_REQUEST);
            //throw new RuntimeException(e);
        }

    }

    @ResponseBody
    @GetMapping(path = "/api/viewOrder")
    public ResponseEntity<List<GetOrderResponseObject> > viewOrder(@RequestParam(value="role") String role, @RequestParam(value="userId") Long userId) {
        System.out.println("Role is " + role);
        System.out.println("UserId is " + userId);
        try {
            List<GetOrderResponseObject> getOrderResponseObjectList = orderService.getOrderList(role, userId);
            return new ResponseEntity<>(getOrderResponseObjectList, HttpStatus.OK);
        }
        catch (Exception e) {
            orderLogger.info(String.valueOf(e));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @DeleteMapping(path="/api/cancelOrder")
    public ResponseEntity<OrderMessage> cancelOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.cancelOrder(orderRequestDTO.getOrderId(), orderRequestDTO.getRole());
    }

    @ResponseBody
    @PatchMapping(path="/api/updateOrderStatus")
    public ResponseEntity<OrderMessage> updateOrderStatus(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.updateOrderStatus(orderRequestDTO.getOrderId(), orderRequestDTO.getRole(), orderRequestDTO.getUpdatedStatus());
    }

    @ResponseBody
    @GetMapping(path="/api/viewHistoricalOrder")
    public ResponseEntity<List<GetOrderResponseObject>> viewHistoricalOrder(@RequestParam("userId") Long userId){
        try {
            return orderService.getHistoricalOrder(userId);
        }
        catch (Exception e) {
            orderLogger.info(String.valueOf(e));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
