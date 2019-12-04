package com.fontys.backend.controllers;

import com.fontys.backend.entities.User;
import com.fontys.backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
    }

    @RequestMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @RequestMapping("/{id}")
    public User getById(@PathVariable("id") int id) {
        return userService.getById(id);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }
}
