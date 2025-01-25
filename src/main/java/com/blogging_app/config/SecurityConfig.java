package com.blogging_app.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Correct way to disable CSRF in Spring Security 6.1
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/blog/api/health").permitAll() // Permit all access to this endpoint
                        .requestMatchers("/blog/api").authenticated() // Require authentication for this endpoint
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Enable HTTP Basic Authentication

        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
