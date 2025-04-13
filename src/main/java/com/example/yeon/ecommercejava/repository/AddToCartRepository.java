package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.AddToCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCartEntity, Long> {

    @Query("SELECT a FROM AddToCartEntity a WHERE users=:userId")
    List<AddToCartEntity> getAllInventoryBySpecificUser(@Param("userId") Long userId);

    @Query("SELECT SUM(a.quantity) FROM AddToCartEntity a WHERE a.inventory=:inventory AND a.users=:userId AND a.selected_color=:selectedColor")
    Integer totalQuantity(@Param("inventory") Long inventoryId, @Param("userId") Long userId, @Param("selectedColor") String selectedColor);

    @Query("SELECT a FROM AddToCartEntity a WHERE a.inventory=:inventory AND a.users=:userId AND a.selected_color=:selectedColor")
    List<AddToCartEntity> getDuplicateAddToCart(@Param("inventory") Long inventoryId, @Param("userId") Long userId, @Param("selectedColor") String selectedColor);
}
