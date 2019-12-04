package com.fontys.backend.controllers;

import com.fontys.backend.entities.User;
import com.fontys.backend.repositories.UserRepository;
import com.fontys.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.hash.Hashing.sha256;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

class UserControllerTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp()
    {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void registerShouldReturnUser()
    {
        User u = new User(1,"User","Salt","Hash");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));

        assertEquals(new User(1,"User"),userService.register(u));
        Mockito.verify(userRepository,times(1)).save(u);
        Mockito.verify(userRepository,times(1)).findById(u.getId());
    }

    @Test
    void loginShouldReturnToken()
    {
        String hash = sha256().hashString("SaltHashSalt", StandardCharsets.UTF_8).toString();
        User u = new User(1,"User","Salt",hash);

        Mockito.when(userRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(u));
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(u);

        assertEquals("{ \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIiwiaXNzIjoiMSJ9.bDJ0pv5R8N1IXCzEKBTVdSp_YNUgOXWzFGKjnwIRkP0\" }",
                userService.login(new User(1,"User","Salt","Hash")));
        Mockito.verify(userRepository,times(1)).findByName(u.getName());
    }

    @Test
    void getAllShouldReturnUsers()
    {
        List<User> userList = Arrays.asList(new User(), new User());

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        assertEquals(userList,userService.getAll());
        Mockito.verify(userRepository,times(1)).findAll();
    }

    @Test
    void getByIdShouldReturnUser()
    {
        User u = new User(1,"User","","");

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));

        assertEquals(u,userService.getById(u.getId()));
        Mockito.verify(userRepository,times(1)).findById(u.getId());
    }
}
