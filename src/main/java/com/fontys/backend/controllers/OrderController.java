package com.fontys.backend.controllers;

import com.fontys.backend.entities.Group;
import com.fontys.backend.entities.Order;
import com.fontys.backend.entities.Product;
import com.fontys.backend.entities.User;
import com.fontys.backend.services.GroupService;
import com.fontys.backend.services.OrderService;
import com.fontys.backend.services.ProductService;
import com.fontys.backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final GroupService groupService;

    public OrderController(OrderService orderService, UserService userService, ProductService productService, GroupService groupService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.groupService = groupService;
    }

    @RequestMapping("/all")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @RequestMapping("/{id}")
    public Order getById(@PathVariable("id") int id) {
        return orderService.getById(id);
    }

    @RequestMapping("/getAllByClaimUser/{id}")
    public List<Order> getAllByClaimUser(@PathVariable("id") int id) {
        User u = userService.getById(id);
        if (u != null) {
            return orderService.getAllByClaimUser(u);
        }
        return null;
    }

    @PostMapping("/create/{groupId}")
    public Order create(@RequestBody Order order, @PathVariable("groupId") int groupId) {
        List<Product> products = new ArrayList<>();
        for (Product p : order.getProducts()) {
            products.add(productService.create(new Product(p.getName(),p.getAmount())));
        }
        User u = userService.getById(order.getUser().getId());
        Group g = groupService.getById(groupId);
        if (u != null && g != null && !products.isEmpty()) {
            Order o = new Order(u,products);
            groupService.addOrder(g, o);
            return orderService.create(o);
        }
        return null;
    }

    @PostMapping("/claim")
    public Boolean claim(@RequestBody Order order) {
        User u = userService.getById(order.getClaimUser().getId());
        Order o = orderService.getById(order.getId());
        if (u != null && o != null) {
            return orderService.claim(u.getId(), o.getId());
        }
        return false;
    }

    @PostMapping("/close")
    public Boolean close(@RequestBody Order order) {
            return orderService.close(order.getId(), order.getPrice());
    }
}
