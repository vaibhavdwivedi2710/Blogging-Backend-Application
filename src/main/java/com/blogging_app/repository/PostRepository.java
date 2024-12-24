package com.blogging_app.repository;

import com.blogging_app.entity.Category;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post>findByTitleContaining(String title);
}
