package com.example.yeon.ecommercejava.dto;

public class UserResponseDTO {
    private String pk;

    public UserResponseDTO(String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "pk='" + pk + '\'' +
                '}';
    }
}
