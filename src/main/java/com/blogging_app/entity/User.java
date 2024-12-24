package com.blogging_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id // Marks this field as the primary key for the entity.
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically generates the primary key.
    private Integer id; // User ID.
    @Column(name = "user_name", nullable = false, length = 30)
    private String name;
    private String email;
    private String password;
    private String about;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post>posts=new ArrayList<>(); // one user can have multiple posts

}
