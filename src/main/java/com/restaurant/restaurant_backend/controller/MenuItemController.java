package com.restaurant.restaurant_backend.controller;

import com.restaurant.restaurant_backend.model.MenuItem;
import com.restaurant.restaurant_backend.service.MenuItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(
            MenuItemService menuItemService) {

        this.menuItemService = menuItemService;
    }

    // Add food item
    @PostMapping
    public ResponseEntity<MenuItem> addItem(
            @RequestBody MenuItem menuItem) {

        return ResponseEntity.ok(
                menuItemService.addItem(menuItem)
        );
    }

    // View all menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllItems() {

        return ResponseEntity.ok(
                menuItemService.getAllItems()
        );
    }

    // Get menu item by ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getItemById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                menuItemService.getItemById(id)
        );
    }

    // Update food item
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateItem(
            @PathVariable Long id,
            @RequestBody MenuItem menuItem) {

        return ResponseEntity.ok(
                menuItemService.updateItem(
                        id,
                        menuItem
                )
        );
    }

    // Delete food item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(
            @PathVariable Long id) {

        menuItemService.deleteItem(id);

        return ResponseEntity.ok(
                "Menu item deleted successfully"
        );
    }

    // Search food items
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> search(
            @RequestParam String name) {

        return ResponseEntity.ok(
                menuItemService.searchByName(name)
        );
    }

    // Filter by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> filterByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                menuItemService.filterByCategory(category)
        );
    }

    // View available items
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableItems() {

        return ResponseEntity.ok(
                menuItemService.getAvailableItems()
        );
    }
}