package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.AddToCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCartEntity, Long> {
}
