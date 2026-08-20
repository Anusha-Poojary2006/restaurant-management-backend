package com.restaurant.restaurant_backend.repository;

import com.restaurant.restaurant_backend.model.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}