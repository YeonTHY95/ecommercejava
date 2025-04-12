package com.example.yeon.ecommercejava.repository;

import java.util.List;

public interface InventoryCardInfo {

    Long getId();
    String getTitle() ;
    String getName();
    Double getRating() ;
    Double getPrice() ;
    String getCategory() ;

    List<String> getColor() ;
    String getImageUrl() ;


}
