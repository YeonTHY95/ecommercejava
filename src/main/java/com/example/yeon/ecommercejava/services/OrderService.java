package com.example.yeon.ecommercejava.services;

import com.example.yeon.ecommercejava.controller.OrderController;
import com.example.yeon.ecommercejava.dto.*;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.entity.OrderEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import com.example.yeon.ecommercejava.events.OrderEvent;
import com.example.yeon.ecommercejava.producer.OrderProducer;
import com.example.yeon.ecommercejava.repository.InventoryRepository;
import com.example.yeon.ecommercejava.repository.OrderRepository;
import com.example.yeon.ecommercejava.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private InventoryRepository inventoryRepository;
    private UserRepository userRepository;

    private final OrderProducer orderProducer;

    public static final Logger orderLogger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, InventoryRepository inventoryRepository,UserRepository userRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.orderProducer = orderProducer;
    }

    public String checkIfInventoryQuantityEnough(Long inventoryId, Integer requestedQuantity){
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId).orElseGet(()-> null);
        if (inventoryEntity == null) {
            orderLogger.info("No Inventory Entity Found for checking Inventory Quantity.");
            return "No Inventory Entity Found for checking Inventory Quantity.";
        }
        else if ( inventoryEntity.getQuantity() >= requestedQuantity) {
            return "true";
        }
        else {
            orderLogger.info("Inventory " + inventoryEntity.getTitle() + " has only left Quantity " + inventoryEntity.getQuantity() + " in Seller's Inventory");
            return "Inventory " + inventoryEntity.getTitle() + " has only left Quantity " + inventoryEntity.getQuantity() + " in Seller's Inventory";
        }
    }

    public boolean makeOrder(Long inventoryId, Integer requestedQuantity, String selectedColor, Long buyerId){
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId).orElseGet(()-> null);
        if (inventoryEntity == null) {
            orderLogger.info("No Inventory Entity Found for checking Inventory Quantity.");
            return false;
        }
        else {
            inventoryEntity.setQuantity(inventoryEntity.getQuantity() - requestedQuantity);
            System.out.println("Seller is " + inventoryEntity.getSellerName());
            // Updated Inventory's Quantity
            inventoryRepository.save(inventoryEntity);
            // Create Order
            UserEntity buyer = userRepository.findById(buyerId).orElseThrow();
            UserEntity seller = inventoryEntity.getSellerObject();
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setInventory(inventoryEntity);
            orderEntity.setBuyer(buyer);
            orderEntity.setSeller(seller);
            orderEntity.setStatus("Pending Seller's Confirmation");
            orderEntity.setQuantity(requestedQuantity);
            orderEntity.setSelectedColor(selectedColor);
            OrderEntity newOrderEntity = orderRepository.save(orderEntity);

            // Create Order Event

            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setId(newOrderEntity.getId());
            orderEvent.setBuyer(buyer.getId());
            orderEvent.setSeller(seller.getId());
            orderEvent.setInventory(inventoryId);
            orderEvent.setStatus("Pending Seller's Confirmation");
            orderEvent.setQuantity(requestedQuantity);
            orderEvent.setSelectedColor(selectedColor);
            orderEvent.setOrderDate(newOrderEntity.getOrderDate());

            orderProducer.sendOrderEvent(orderEvent);

            return true;
        }
    }

    public List<GetOrderResponseObject> getOrderList(String role, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        List<OrderEntity> orderList = orderRepository.getOrderListByUserId(user);
        List<GetOrderResponseObject> responseList = new ArrayList<>();
        for(OrderEntity order : orderList) {
            InventoryEntity inventoryEntity = order.getInventory();
            InventoryCardInfoDTO inventoryCardInfoDTO = modelMapper.map(inventoryEntity, InventoryCardInfoDTO.class);
            GetOrderResponseObject orderResponseObject = new GetOrderResponseObject();
            orderResponseObject.setInventory(inventoryCardInfoDTO);
            orderResponseObject.setOrderId(order.getId());
            orderResponseObject.setBuyer(order.getBuyer().getUsername());
            orderResponseObject.setSeller(order.getSeller().getUsername());
            orderResponseObject.setStatus(order.getStatus());
            orderResponseObject.setOrderDate(order.getOrderDate());
            orderResponseObject.setQuantity(order.getQuantity());
            orderResponseObject.setSelectedColor(order.getSelectedColor());
            responseList.add(orderResponseObject);
        }

        // Show the Order by OrderDate in Descending order
        List<GetOrderResponseObject> sortByOrderDateResponseList = responseList.stream().sorted(Comparator.comparing(GetOrderResponseObject::getOrderDate).reversed()).collect(Collectors.toList());
        System.out.println("sortByOrderDateResponseList is " + sortByOrderDateResponseList);
        return sortByOrderDateResponseList;
    }

    public ResponseEntity<OrderMessage> cancelOrder(Long orderId, String role)
    {
        try {
            orderLogger.info("Cancelled Order ID is " + orderId);
            OrderEntity cancelledOrder = orderRepository.findById(orderId).orElseThrow();
            orderLogger.info("Inside OrderService's Cancel Order");
            orderLogger.info("Role is " + role);
            if ( role.equals("Buyer")) {
                cancelledOrder.setStatus("Cancelled by Buyer");
                orderRepository.save(cancelledOrder);
                orderLogger.info("Order Cancelled Successfully by Buyer");
                return new ResponseEntity<>(new OrderMessage("Order Cancelled Successfully by Buyer"), HttpStatus.OK);
            }
            else if (role.equals("Seller")) {
                cancelledOrder.setStatus("Cancelled by Seller");
                orderRepository.save(cancelledOrder);
                orderLogger.info("Order Cancelled Successfully by Seller");
                return new ResponseEntity<>(new OrderMessage("Order Cancelled Successfully by Seller"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new OrderMessage("Error in Response"), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            orderLogger.info(String.valueOf(e));
            return new ResponseEntity<>(new OrderMessage("Error in cancelOrder : " + String.valueOf(e)), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<OrderMessage> updateOrderStatus(Long orderId, String role, String updatedStatus) {
        try {
            orderLogger.info("Inside updateOrderStatus");
            orderLogger.info("Order ID : " + orderId);
            orderLogger.info("Role : " + role);
            orderLogger.info("updatedStatus : " + updatedStatus);
            OrderEntity updatedOrder = orderRepository.findById(orderId).orElseThrow();
            updatedOrder.setStatus(updatedStatus);
            orderRepository.save(updatedOrder);
            System.out.println("Update Order Status : orderId : " + orderId + " role : " + role + " updatedStatus : " + updatedStatus);
            return new ResponseEntity<>(new OrderMessage("Order Status Updated Successfully"), HttpStatus.OK);
        }
        catch (Exception e) {
            orderLogger.info(String.valueOf(e));
            return new ResponseEntity<>(new OrderMessage("Error in updated Order Status : " + String.valueOf(e)), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<GetOrderResponseObject>> getHistoricalOrder(Long userId){
        try {
            UserEntity user = userRepository.findById(userId).orElseThrow();
            List<OrderEntity> historicalOrderList = orderRepository.getHistoricalOrderListByUserId(user);
            List<GetOrderResponseObject> responseList = new ArrayList<>();
            for(OrderEntity order : historicalOrderList) {
                InventoryEntity inventoryEntity = order.getInventory();
                InventoryCardInfoDTO inventoryCardInfoDTO = modelMapper.map(inventoryEntity, InventoryCardInfoDTO.class);
                GetOrderResponseObject orderResponseObject = new GetOrderResponseObject();
                orderResponseObject.setInventory(inventoryCardInfoDTO);
                orderResponseObject.setOrderId(order.getId());
                orderResponseObject.setBuyer(order.getBuyer().getUsername());
                orderResponseObject.setSeller(order.getSeller().getUsername());
                orderResponseObject.setStatus(order.getStatus());
                orderResponseObject.setOrderDate(order.getOrderDate());
                orderResponseObject.setQuantity(order.getQuantity());
                orderResponseObject.setSelectedColor(order.getSelectedColor());
                responseList.add(orderResponseObject);
            }
            List<GetOrderResponseObject> sortedByOrderDateHistoricalOder = responseList.stream().sorted(Comparator.comparing(GetOrderResponseObject::getOrderDate).reversed()).collect(Collectors.toList());
            System.out.println("sortedByOrderDateHistoricalOder is " + sortedByOrderDateHistoricalOder);
            return new ResponseEntity<>(sortedByOrderDateHistoricalOder, HttpStatus.OK);
        }
        catch (Exception e) {
            orderLogger.info(String.valueOf(e));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
