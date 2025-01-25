package com.blogging_app.repository;

import java.util.Optional;
import com.blogging_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

}

