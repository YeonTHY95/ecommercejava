package com.example.yeon.ecommercejava.dto;

public class AddToCartGetResponseDTO {
    private InventoryDTO inventoryDTO;
    private Integer quantity;
    private String selectedColor ;

    public AddToCartGetResponseDTO(InventoryDTO inventoryDTO, Integer quantity, String selectedColor) {
        this.inventoryDTO = inventoryDTO;
        this.quantity = quantity;
        this.selectedColor = selectedColor;
    }


}
