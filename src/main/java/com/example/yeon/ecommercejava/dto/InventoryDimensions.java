package com.example.yeon.ecommercejava.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public class InventoryDimensions {

    Double width;
    Double height;
    Double length;

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}
