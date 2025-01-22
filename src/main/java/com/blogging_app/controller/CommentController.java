package com.blogging_app.controller;

import com.blogging_app.dto.ApiResponse;
import com.blogging_app.dto.CommentDto;
import com.blogging_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto>createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId )
    {
     CommentDto createComment = this.commentService.createComment(commentDto,postId);
     return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/delete/{commentId}")
    public ResponseEntity<ApiResponse>deleteComment(
            @PathVariable Integer commentId )
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
    }

}
