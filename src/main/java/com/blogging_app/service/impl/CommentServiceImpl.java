package com.blogging_app.service.impl;

import com.blogging_app.dto.CommentDto;
import com.blogging_app.dto.PostDto;
import com.blogging_app.entity.Comment;
import com.blogging_app.entity.Post;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.CommentRepository;
import com.blogging_app.repository.PostRepository;
import com.blogging_app.service.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        logger.debug("Processing request to create a comment for post ID: {}", postId);

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> {
                    logger.error("Post with ID {} not found", postId);
                    return new ResourceNotFoundException("Post", "postId", postId);
                });

        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {
        logger.debug("Processing request to delete comment with ID: {}", commentId);

        // Fetch comment by ID
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    logger.error("Comment with ID {} not found", commentId);
                    return new ResourceNotFoundException("Comment", "commentId", commentId);
                });
        this.commentRepository.delete(comment);
    }
}
