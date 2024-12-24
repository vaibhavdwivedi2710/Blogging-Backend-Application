package com.blogging_app.dto;

import com.blogging_app.entity.Category;
import com.blogging_app.entity.Comment;
import com.blogging_app.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {

    private Integer postId;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;

    private String imageName;

    private Date addedDate;

    //using post and user DTOs Otherwise it will create recursion
    private CategoryDto category;

    private UserDto user;

    //comment will fetch automatically with post
    private Set<CommentDto>commentDto = new HashSet<>();

}
