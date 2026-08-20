package com.restaurant.restaurant_backend.service;

import com.restaurant.restaurant_backend.model.MenuItem;
import com.restaurant.restaurant_backend.repository.MenuItemRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem addItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAllItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem getItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Menu item not found"
                        )
                );
    }

    public MenuItem updateItem(
            Long id,
            MenuItem updatedItem) {

        MenuItem existingItem = getItemById(id);

        existingItem.setName(updatedItem.getName());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setDescription(
                updatedItem.getDescription()
        );
        existingItem.setImageUrl(
                updatedItem.getImageUrl()
        );
        existingItem.setAvailable(
                updatedItem.getAvailable()
        );

        return menuItemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        MenuItem existingItem = getItemById(id);
        menuItemRepository.delete(existingItem);
    }

    public List<MenuItem> searchByName(String name) {
        return menuItemRepository
                .findByNameContainingIgnoreCase(name);
    }

    public List<MenuItem> filterByCategory(
            String category) {

        return menuItemRepository
                .findByCategoryIgnoreCase(category);
    }

    public List<MenuItem> getAvailableItems() {
        return menuItemRepository.findByAvailableTrue();
    }
}