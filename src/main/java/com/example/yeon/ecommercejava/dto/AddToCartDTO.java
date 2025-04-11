package com.example.yeon.ecommercejava.dto;

import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AddToCartDTO {

    Long id ;

    private InventoryDTO inventory ; // Foreign Key

    private UserDTO user ; // Foreign Key
    private Integer quantity ;
    private String selectedColor ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryDTO inventory) {
        this.inventory = inventory;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
