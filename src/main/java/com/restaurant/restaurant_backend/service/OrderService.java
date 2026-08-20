package com.restaurant.restaurant_backend.service;

import com.restaurant.restaurant_backend.model.MenuItem;
import com.restaurant.restaurant_backend.model.Order;
import com.restaurant.restaurant_backend.model.OrderItem;
import com.restaurant.restaurant_backend.model.OrderStatus;
import com.restaurant.restaurant_backend.repository.MenuItemRepository;
import com.restaurant.restaurant_backend.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            MenuItemRepository menuItemRepository) {

        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Order placeOrder(
            Long userId,
            Order order) {

        order.setId(null);
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);

        double totalPrice = 0.0;

        for (OrderItem item : order.getItems()) {

            MenuItem menuItem =
                    menuItemRepository.findById(
                            item.getFoodId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Food item not found: "
                                            + item.getFoodId()
                            )
                    );

            if (!Boolean.TRUE.equals(
                    menuItem.getAvailable())) {

                throw new RuntimeException(
                        "Food item is not available: "
                                + menuItem.getName()
                );
            }

            if (item.getQuantity() == null ||
                    item.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Quantity must be greater than 0"
                );
            }

            item.setId(null);
            item.setOrder(order);

            totalPrice +=
                    menuItem.getPrice()
                            * item.getQuantity();
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );
    }

    public List<Order> getMyOrders(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    public Order updateOrderStatus(
            Long id,
            OrderStatus status) {

        Order order = getOrderById(id);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByStatus(
            OrderStatus status) {

        return orderRepository.findByStatus(status);
    }
}