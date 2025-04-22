package com.example.yeon.ecommercejava.events;

import com.example.yeon.ecommercejava.dto.CartInterface;
import com.example.yeon.ecommercejava.dto.InventoryDTO;
import com.example.yeon.ecommercejava.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class OrderEvent {

    Long id ;

    private String status ; // ["Pending Seller's Confirmation","Cancelled by Buyer","Cancelled by Seller","Confirmed by Seller","Shipped","Delivered by Seller","Received by Buyer"]

    private long buyer ; // Foreign Key

    private long seller ; // Foreign Key

    private LocalDateTime orderDate = LocalDateTime.now();

    private long inventory ; // Foreign Key
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

    public long getBuyer() {
        return buyer;
    }

    public void setBuyer(long buyer) {
        this.buyer = buyer;
    }

    public long getSeller() {
        return seller;
    }

    public void setSeller(long seller) {
        this.seller = seller;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
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

    @Override
    public String toString() {
        return "OrderEvent{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", orderDate=" + orderDate +
                ", inventory=" + inventory +
                ", quantity=" + quantity +
                ", selectedColor='" + selectedColor + '\'' +
                '}';
    }
}
