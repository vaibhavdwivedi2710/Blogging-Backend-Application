package com.blogging_app.service.impl;

import com.blogging_app.dto.CategoryDto;
import com.blogging_app.dto.PostDto;
import com.blogging_app.dto.PostResponse;
import com.blogging_app.entity.Category;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.User;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.CategoryRepository;
import com.blogging_app.repository.PostRepository;
import com.blogging_app.repository.UserRepository;
import com.blogging_app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {

        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","user id", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","category Id",categoryId));

        Post post =  this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepository.save(post);

        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDirection) {

         Sort sort = (sortDirection.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());

        // implementing Paging and sorting on this
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort );

        Page<Post> pagePost = this.postRepository.findAll(pageable);
        List<Post> allPosts = pagePost.getContent();

        //List<Post>posts=this.postRepository.findAll();
        List<PostDto> postDtos = allPosts.stream()
                .map(post->this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        //returning pastResponse for proper response
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {

        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        //fetch post using pagination
        Page<Post> pagePosts = this.postRepository.findByCategory(cat,pageable);
        List<Post>posts = pagePosts.getContent();

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        // Create PostResponse for pagination details
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber,Integer pageSize) {

        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePosts = this.postRepository.findByUser(user,pageable);
        List<Post>posts = pagePosts.getContent();

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        // Create PostResponse for pagination details
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post>posts = this.postRepository.findByTitleContaining(keyword);
        List<PostDto> PostDto = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return PostDto;
    }
}
