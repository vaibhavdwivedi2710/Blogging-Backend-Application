package com.blogging_app.controller;


import com.blogging_app.config.AppConstants;
import com.blogging_app.dto.ApiResponse;
import com.blogging_app.dto.PostDto;
import com.blogging_app.dto.PostResponse;
import com.blogging_app.entity.Post;
import com.blogging_app.service.FileService;
import com.blogging_app.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${Upload.FilePath}")
    private String path;


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    ResponseEntity<PostDto>createPost(
            @RequestBody PostDto postdto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId)
    {
       PostDto createdPost = this.postService.createPost(postdto,userId,categoryId);
       return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //get Posts by User
    @GetMapping("/user/{userId}/posts")
    ResponseEntity<PostResponse> getPostsByUser(
            @PathVariable Integer userId,
            @RequestParam(value="pageNumber",defaultValue ="0",required = false)Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue ="10",required = false) Integer pageSize
    )
    {
        PostResponse postResponse = this.postService.getPostsByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //get Posts By Category
    @GetMapping("/category/{categoryId}/posts")
    ResponseEntity<PostResponse> getPostsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value="pageNumber",defaultValue ="0",required = false)Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue ="10",required = false) Integer pageSize
    )
    {
        PostResponse postResponse = this.postService.getPostsByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //get All Posts
    @GetMapping("/posts/")
    public ResponseEntity<PostResponse>getAllPosts(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection)
    {
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirection);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //get Post by Post ID
    @GetMapping("/posts/{postId}")
    ResponseEntity<PostDto>getPostById(@PathVariable Integer postId){
        return ResponseEntity.ok(this.postService.getPostById(postId));
    }

    //Update Post By Post Id
    @PutMapping("/posts/update/{postId}")
    ResponseEntity<?>updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    //Delete POST by Post ID
    @DeleteMapping("/posts/delete/{postId}")
    ResponseEntity<ApiResponse>deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>( new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }

    //search post
    @GetMapping("/posts/search/{keywords}")
    ResponseEntity<List<PostDto>>searchPostByTitle(
            @PathVariable("keywords")String keywords)
    {
      List<PostDto> result = this.postService.searchPost(keywords);
      return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }


    //post image upload
    //TODO: Implement this login while creating post only
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId) throws IOException {
       PostDto postDto = this.postService.getPostById(postId);

       String fileName = this.fileService.uploadImage(path,image);
       postDto.setImageName(fileName);
       PostDto updatedPost = this.postService.updatePost(postDto,postId);
       return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    //method to serve files
    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
