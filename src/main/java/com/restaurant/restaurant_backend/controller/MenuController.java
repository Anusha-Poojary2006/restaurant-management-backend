package com.restaurant.restaurant_backend.controller;

import com.restaurant.restaurant_backend.model.MenuItem;
import com.restaurant.restaurant_backend.repository.MenuItemRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuItemRepository menuItemRepository;

    public MenuController(
            MenuItemRepository menuItemRepository) {

        this.menuItemRepository = menuItemRepository;
    }

    // View all menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {

        return ResponseEntity.ok(
                menuItemRepository.findAll()
        );
    }

    // Search menu by name
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenu(
            @RequestParam String name) {

        return ResponseEntity.ok(
                menuItemRepository
                        .findByNameContainingIgnoreCase(name)
        );
    }

    // Filter menu by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                menuItemRepository.findByCategoryIgnoreCase(
                        category
                )
        );
    }

    // Admin adds menu item
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> createMenuItem(
            @RequestBody MenuItem menuItem) {

        menuItem.setId(null);

        return ResponseEntity.ok(
                menuItemRepository.save(menuItem)
        );
    }

    // Admin updates menu item
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuItem menuItem) {

        MenuItem existing =
                menuItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu item not found"
                                )
                        );

        existing.setName(menuItem.getName());
        existing.setCategory(menuItem.getCategory());
        existing.setPrice(menuItem.getPrice());
        existing.setDescription(
                menuItem.getDescription()
        );
        existing.setImageUrl(
                menuItem.getImageUrl()
        );
        existing.setAvailable(
                menuItem.getAvailable()
        );

        return ResponseEntity.ok(
                menuItemRepository.save(existing)
        );
    }

    // Admin deletes menu item
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMenuItem(
            @PathVariable Long id) {

        if (!menuItemRepository.existsById(id)) {

            return ResponseEntity.notFound().build();
        }

        menuItemRepository.deleteById(id);

        return ResponseEntity.ok(
                "Menu item deleted successfully"
        );
    }
}