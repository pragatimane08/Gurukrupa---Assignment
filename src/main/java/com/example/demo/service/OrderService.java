package com.example.demo.service;

import com.example.demo.Entity.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create a new order
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);  // Set the default status as PENDING

        // Calculate the total amount based on order items
        BigDecimal totalAmount = order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        // Set the order to each order item
        order.getOrderItems().forEach(item -> item.setOrder(order));

        // Save the order along with its items
        return orderRepository.save(order);
    }

    // Get all orders for a user
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    // Get order by ID
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    // Update the order status
    public Order updateOrderStatus(Integer orderId, Order.OrderStatus status) {
        // Fetch the order from the database
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null; // If order is not found, return null
    }

    // Save the order (if needed for updates)
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Fetch a single order by ID
    public Order findOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);  // Return null if not found
    }

    public Order processOrder(Order order, String paymentStatus) {
        if ("succeeded".equals(paymentStatus)) {
            order.setStatus(Order.OrderStatus.COMPLETED);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Payment failed");
        }
    }

}
