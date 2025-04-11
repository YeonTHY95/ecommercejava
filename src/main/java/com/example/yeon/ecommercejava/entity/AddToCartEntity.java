package com.example.yeon.ecommercejava.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "AddToCarts")
public class AddToCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "inventory")
    private InventoryEntity inventory ; // Foreign Key

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "users")
    private UserEntity user ; // Foreign Key
    private Integer quantity ;
    private String selectedColor ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public AddToCartEntity(Long id, InventoryEntity inventory, UserEntity user, Integer quantity, String selectedColor) {
//        this.id = id;
//        this.inventory = inventory;
//        this.user = user;
//        this.quantity = quantity;
//        this.selectedColor = selectedColor;
//    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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
