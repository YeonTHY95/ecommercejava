package com.example.yeon.ecommercejava.dto;

public class ResponseErrorDTO {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResponseErrorDTO(String error) {
        this.error = error;
    }
}
