package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.InventoryEntity;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    @Query("SELECT DISTINCT i.category FROM InventoryEntity i")
    List<String> findDistinctCategoryBy();

//    @Query("SELECT i.title, i.id, i.name, i.price, i.imageUrl, i.rating, i.category, i.color FROM InventoryEntity i ORDER BY hotSalesScore DESC LIMIT 5")
    @Query("SELECT i FROM InventoryEntity i ORDER BY i.hotSalesScore DESC")
    List<InventoryCardInfo> getHotSalesScoreInventory(Pageable pageable);

    @Query(value = "SELECT * FROM inventories i ORDER BY i.hot_sales_score DESC LIMIT 5", nativeQuery = true)
    List<InventoryCardInfo> getHotSalesScoreInventoryNativeSQL();
}
