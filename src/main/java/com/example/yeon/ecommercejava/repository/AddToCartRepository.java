package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.AddToCartEntity;
import com.example.yeon.ecommercejava.entity.InventoryEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCartEntity, Long> {

    @Query("SELECT a FROM AddToCartEntity a WHERE a.user=:userId")
    List<AddToCartEntity> getAllInventoryBySpecificUser(@Param("userId") UserEntity userId);

    @Query("SELECT SUM(a.quantity) FROM AddToCartEntity a WHERE a.inventory=:inventory AND a.user=:userId AND a.selectedColor=:selectedColor")
    Integer totalQuantity(@Param("inventory") InventoryEntity inventory, @Param("userId") UserEntity userId, @Param("selectedColor") String selectedColor);

    @Query("SELECT a FROM AddToCartEntity a WHERE a.inventory=:inventory AND a.user=:userId AND a.selectedColor=:selectedColor")
    List<AddToCartEntity> getDuplicateAddToCart(@Param("inventory") InventoryEntity inventory, @Param("userId") UserEntity userId, @Param("selectedColor") String selectedColor);
}
