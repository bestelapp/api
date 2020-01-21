package com.fontys.backend.repositories;

import com.fontys.backend.entities.Order;
import com.fontys.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getAllByClaimUser(User user);
}
