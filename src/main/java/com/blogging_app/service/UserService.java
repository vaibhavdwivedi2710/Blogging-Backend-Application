package com.blogging_app.service;

import com.blogging_app.dto.UserDto;
import com.blogging_app.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto User);

    UserDto updateUser(UserDto user,Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

}
