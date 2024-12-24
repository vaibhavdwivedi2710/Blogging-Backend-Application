package com.blogging_app.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDto {

    private Integer commentId;

    @NotBlank(message = "Content cannot be blank")
    private String content;

}
