package com.example.yeon.ecommercejava.dto;

import java.util.Date;

public class LoginResponse {
    private String token;

    private Date expiresIn;

    public String getToken() {
        return token;
    }

    private UserResponseDTO user ;

    public LoginResponse(String token, Date expiresIn, UserResponseDTO user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", user=" + user +
                '}';
    }
}
