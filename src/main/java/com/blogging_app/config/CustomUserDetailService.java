package com.blogging_app.config;

import com.blogging_app.entity.User;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading username from database
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", username));

        return user; //returning user in place of user details( as we have implemented UserDetails interface in User entity)
    }
}
