package com.example.yeon.ecommercejava.dto;

import java.util.List;

public class MakeOrderRequestDTO {
    private Long user;
    private Double totalPrice;
    private List<CartInterface> orderList;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartInterface> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CartInterface> orderList) {
        this.orderList = orderList;
    }
}
