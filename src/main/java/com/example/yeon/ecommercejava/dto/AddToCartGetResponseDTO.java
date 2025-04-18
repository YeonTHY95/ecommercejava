package com.example.yeon.ecommercejava.dto;

public class AddToCartGetResponseDTO {
    private InventoryDTO inventory;
    private Integer quantity;
    private String selectedColor ;

    public AddToCartGetResponseDTO(InventoryDTO inventory, Integer quantity, String selectedColor) {
        this.inventory = inventory;
        this.quantity = quantity;
        this.selectedColor = selectedColor;
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
