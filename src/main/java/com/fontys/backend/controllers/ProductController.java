package com.fontys.backend.controllers;

import com.fontys.backend.entities.Product;
import com.fontys.backend.services.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/all")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @RequestMapping("/{id}")
    public Product getById(@PathVariable("id") int id) {
        return productService.getById(id);
    }
}
