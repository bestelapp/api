package com.fontys.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column
    private int id;

    @Column
    private String name;

    @Column
    private double price;

    public Product(String name) {
        this.name = name;
    }
}
