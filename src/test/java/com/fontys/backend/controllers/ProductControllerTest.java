package com.fontys.backend.controllers;

import com.fontys.backend.entities.Product;
import com.fontys.backend.repositories.ProductRepository;
import com.fontys.backend.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

class ProductControllerTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp()
    {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void createShouldReturnProduct()
    {
        Product p = new Product();

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(p);

        assertEquals(p,productService.create(p));
        Mockito.verify(productRepository,times(1)).save(p);
    }

    @Test
    void getAllShouldReturnProducts()
    {
        List<Product> productList = Arrays.asList(new Product(),new Product());

        Mockito.when(productRepository.findAll()).thenReturn(productList);

        assertEquals(productList,productService.getAll());
        Mockito.verify(productRepository,times(1)).findAll();
    }

    @Test
    void getByIdShouldReturnOrder()
    {
        Product p = new Product(1,"Product",1);

        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(p));

        assertEquals(p,productService.getById(p.getId()));
        Mockito.verify(productRepository,times(1)).findById(p.getId());
    }
}
