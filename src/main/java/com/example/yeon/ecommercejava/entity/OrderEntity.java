package com.example.yeon.ecommercejava.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id ;

    private String status ; // ["Pending Seller's Confirmation","Cancelled by Buyer","Cancelled by Seller","Confirmed by Seller","Shipped","Delivered by Seller","Received by Buyer"]

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "buyer")
    private UserEntity buyer ; // Foreign Key

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "seller")
    private UserEntity seller ; // Foreign Key

    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "inventory")
    private InventoryEntity inventory ; // Foreign Key
    private Integer quantity ;
    private String selectedColor ;


//    public OrderEntity(Long id, String status, UserEntity buyer, UserEntity seller, LocalDateTime orderDate, InventoryEntity inventory, Integer quantity, String selectedColor) {
//        this.id = id;
//        this.status = status;
//        this.buyer = buyer;
//        this.seller = seller;
//        this.orderDate = orderDate;
//        this.inventory = inventory;
//        this.quantity = quantity;
//        this.selectedColor = selectedColor;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity seller) {
        this.seller = seller;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }
}
