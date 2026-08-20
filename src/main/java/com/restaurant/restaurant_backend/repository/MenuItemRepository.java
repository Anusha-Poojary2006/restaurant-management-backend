package com.restaurant.restaurant_backend.repository;

import com.restaurant.restaurant_backend.model.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository
        extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByCategoryIgnoreCase(String category);

    List<MenuItem> findByNameContainingIgnoreCase(String name);

    List<MenuItem> findByAvailableTrue();
}