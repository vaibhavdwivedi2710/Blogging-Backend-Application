package com.blogging_app.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/blog/ui")
public class BlogUIController {


    // Home Page
    @GetMapping("/home")
    public String getHomePage() {
        // Return the Thymeleaf template for the home page
        return "index"; // This corresponds to src/main/resources/templates/index.html
    }

//    @GetMapping("/home")
//    public String getHomePage() {
//        // Retrieve a list of featured posts
//        //List<Post> featuredPosts = postService.getFeaturedPosts();
//        //model.addAttribute("featuredPosts", featuredPosts);
//        return "index";
//    }

}
