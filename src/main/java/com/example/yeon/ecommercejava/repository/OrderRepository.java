package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT order FROM OrderEntity order " +
            "WHERE (order.buyer.id = :userId OR order.seller.id = :userId) " +
            "AND order.status NOT ILIKE CONCAT('%', 'Cancelled','%') " +
            "AND order.status <> 'Received by Buyer' ")
    List<OrderEntity> getOrderListByUserId(@Param("userId") Long userId) ;

    @Query("SELECT order FROM OrderEntity order " +
            "WHERE (order.buyer.id = :userId OR order.seller.id = :userId) " +
            "AND order.status ILIKE CONCAT('%', 'Cancelled','%') " +
            "AND order.status = 'Received by Buyer' ")
    List<OrderEntity> getHistoricalOrderListByUserId(@Param("userId") Long userId) ;
}
