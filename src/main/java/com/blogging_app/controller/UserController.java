package com.blogging_app.controller;

import com.blogging_app.dto.ApiResponse;
import com.blogging_app.dto.UserDto;
import com.blogging_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Post API for creating a user
    @PostMapping("/create")
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    // Update Existing User
    @PutMapping("/update/{userId}") //userid is path variable here
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
      UserDto updatedUser = this.userService.updateUser(userDto,userId);
      return ResponseEntity.ok(updatedUser);
    }

    //Delete a User
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        this.userService.deleteUser(userId);
        //return new ResponseEntity(Map.of("message", "User Deleted Successfully"),HttpStatus.OK);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true),HttpStatus.OK);
    }

    //Get all Users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>>getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //Get User by Id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

}
