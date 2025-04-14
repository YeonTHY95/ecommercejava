package com.example.yeon.ecommercejava.dto;

import java.time.LocalDateTime;

public class GetOrderResponseObject {

    private Long orderId;
    private InventoryCardInfoDTO inventory;
    private String buyer;
    private String seller;
    private String status;
    private LocalDateTime orderDate;
    private Integer quantity;
    private String selectedColor;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public InventoryCardInfoDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryCardInfoDTO inventory) {
        this.inventory = inventory;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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
