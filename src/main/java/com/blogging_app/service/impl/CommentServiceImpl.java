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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));

        Comment comment = this.modelMapper.map(commentDto,Comment.class);

        comment.setPost(post);

        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));
        this.commentRepository.delete(comment);
    }
}
