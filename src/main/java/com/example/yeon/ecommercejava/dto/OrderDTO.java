package com.example.yeon.ecommercejava.dto;

import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class OrderDTO {
    Long id ;

    private String status ; // ["Pending Seller's Confirmation","Cancelled by Buyer","Cancelled by Seller","Confirmed by Seller","Shipped","Delivered by Seller","Received by Buyer"]

    private UserDTO buyer ; // Foreign Key

    private UserDTO seller ; // Foreign Key

    private Date orderDate = new Date();

    private InventoryDTO inventory ; // Foreign Key
    private Integer quantity ;
    private String selectedColor ;

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

    public UserDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDTO buyer) {
        this.buyer = buyer;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public InventoryDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryDTO inventory) {
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
