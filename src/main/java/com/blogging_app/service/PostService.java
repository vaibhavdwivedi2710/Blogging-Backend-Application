package com.blogging_app.service;

import com.blogging_app.dto.PostDto;
import com.blogging_app.dto.PostResponse;
import com.blogging_app.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto,Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    PostDto getPostById(Integer postId);

    //get all post by category
    PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

    //get all posts by User
    PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);

    //search posts
    List<PostDto>searchPost(String keyword);
}
