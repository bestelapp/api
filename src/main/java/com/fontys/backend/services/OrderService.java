package com.fontys.backend.services;

import com.fontys.backend.entities.Order;
import com.fontys.backend.entities.User;
import com.fontys.backend.repositories.OrderRepository;
import com.fontys.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order getById(int id) {
        Optional<Order> user = orderRepository.findById(id);
        return user.orElse(null);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getAllByClaimUser(User user) {
        return orderRepository.getAllByClaimUser(user);
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Boolean claim(int userId, int orderId) {
        Optional<User> user = userRepository.findById(userId);
        Order order = getById(orderId);
        if (user.isPresent() && order != null) {
            order.setClaimUser(user.get());
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public Boolean close(int orderId, double price) {
        Order order = getById(orderId);
        if (order != null) {
            order.setPrice(price);
            orderRepository.save(order);
            return true;
        }
        return false;
    }
}
