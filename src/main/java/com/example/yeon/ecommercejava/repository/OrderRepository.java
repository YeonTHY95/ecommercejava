package com.example.yeon.ecommercejava.repository;

import com.example.yeon.ecommercejava.entity.OrderEntity;
import com.example.yeon.ecommercejava.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT order FROM OrderEntity order " +
            "WHERE (order.buyer = :user OR order.seller = :user) " +
            "AND (order.status NOT ILIKE CONCAT('%', 'Cancelled','%') " +
            "AND order.status <> 'Received by Buyer' )")
    List<OrderEntity> getOrderListByUserId(@Param("user") UserEntity user) ;

    @Query("SELECT order FROM OrderEntity order " +
            "WHERE (order.buyer = :user OR order.seller = :user) " +
            "AND (order.status ILIKE CONCAT('%', 'Cancelled','%') " +
            "OR order.status = 'Received by Buyer' )")
    List<OrderEntity> getHistoricalOrderListByUserId(@Param("user") UserEntity user) ;
}
