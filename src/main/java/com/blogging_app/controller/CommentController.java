package com.blogging_app.controller;

import com.blogging_app.dto.ApiResponse;
import com.blogging_app.dto.CommentDto;
import com.blogging_app.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/api/")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto>createComment( @RequestBody CommentDto commentDto, @PathVariable Integer postId ) {

        logger.info("Received request to create a comment for post ID: {} with request body: {}", postId, commentDto);
        CommentDto createComment = this.commentService.createComment(commentDto,postId);
        logger.info("Comment created successfully for post ID: {} with response: {}", postId, createComment);
        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/delete/{commentId}")
    public ResponseEntity<ApiResponse>deleteComment( @PathVariable Integer commentId ) {

        logger.info("Received request to delete comment with ID: {}", commentId);
        this.commentService.deleteComment(commentId);
        logger.info("Comment deleted successfully with ID: {}", commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
    }

}
