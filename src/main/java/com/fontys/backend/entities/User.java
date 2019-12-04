package com.fontys.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column
    private int id;

    @Column(unique = true)
    private String name;

    @Column
    private String salt;

    @Column
    private String hash;

    public User(String name, String salt, String hash) {
        this.name = name;
        this.salt = salt;
        this.hash = hash;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }
}
