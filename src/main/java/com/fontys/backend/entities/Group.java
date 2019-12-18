package com.fontys.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column
    private int id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> users;

    public Group(int id) {
        this.id = id;
        this.users = new ArrayList<>();
    }

    public Group(String name) {
        this.name = name;
        this.users = new ArrayList<>();
    }

    public Group(String name, User owner, List<User> users) {
        this.name = name;
        this.owner = owner;
        this.users = users;
    }
}
