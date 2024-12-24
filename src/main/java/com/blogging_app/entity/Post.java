package com.blogging_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name="post_title",length = 100,nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date addedDate;

    @ManyToOne//multiple posts have single category
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne // multiple posts have single user
    private User user;

    // using 'mappedBy' taki jo foreign key bano wo sirf 'post' table me bane
    // to avoid infinite loop.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

}
