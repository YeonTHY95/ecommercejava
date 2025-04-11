package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    @Query("SELECT DISTINCT i.category FROM InventoryEntity i")
    List<String> findDistinctCategoryBy();
}
