package com.fontys.backend.controllers;

import com.fontys.backend.entities.Order;
import com.fontys.backend.entities.Product;
import com.fontys.backend.entities.User;
import com.fontys.backend.repositories.OrderRepository;
import com.fontys.backend.repositories.UserRepository;
import com.fontys.backend.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

class OrderControllerTest {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp()
    {
        orderRepository = Mockito.mock(OrderRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        orderService = new OrderService(orderRepository, userRepository);
    }

    @Test
    void createShouldReturnOrder()
    {
        List<Product> productList = Collections.singletonList(new Product());
        Order o = new Order(new User(),productList);

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(o);

        assertEquals(o,orderService.create(o));
        Mockito.verify(orderRepository,times(1)).save(o);
    }

    @Test
    void getAllShouldReturnOrders()
    {
        List<Product> productList = Collections.singletonList(new Product());
        List<Order> orderList = Collections.singletonList(new Order(new User(),productList));

        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        assertEquals(orderList,orderService.getAll());
        Mockito.verify(orderRepository,times(1)).findAll();
    }

    @Test
    void getByIdShouldReturnOrder()
    {
        List<Product> productList = Collections.singletonList(new Product());
        Order o = new Order(1,new User(),new User(),productList);

        Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(o));

        assertEquals(o,orderService.getById(o.getId()));
        Mockito.verify(orderRepository,times(1)).findById(o.getId());
    }

    @Test
    void claimOrderShouldReturnOrder()
    {
        List<Product> productList = Collections.singletonList(new Product());
        User u = new User("UserOne","","");
        Order oWithout = new Order(1,u,null,productList);
        Order oWith = new Order(1,u,new User("UserOne","",""),productList);

        Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(oWithout));
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));

        assertTrue(orderService.claim(u.getId(),oWithout.getId()));
        Mockito.verify(userRepository,times(1)).findById(u.getId());
        Mockito.verify(orderRepository,times(1)).findById(oWithout.getId());
        Mockito.verify(orderRepository,times(1)).save(oWith);
    }
}
