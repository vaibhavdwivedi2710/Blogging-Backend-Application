package com.blogging_app.service.impl;

import com.blogging_app.dto.UserDto;
import com.blogging_app.entity.User;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.UserRepository;
import com.blogging_app.service.UserService;
import com.blogging_app.utils.LoggerFactoryUtil;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactoryUtil.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating a new user with name: {}", userDto.getName());
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        logger.info("User created with name: {} and id:{}", savedUser.getName(), savedUser.getId());
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        logger.info("Updating user with id: {}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User","Id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepository.save(user);
        logger.info("User with name: {} and id: {} updated successfully", updatedUser.getName(), updatedUser.getId());
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        logger.info("Fetching user with id: {}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        logger.info("User found with id: {}", userId);
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = this.userRepository.findAll();
        //used lamba ki expresion API
        List<UserDto> userDtos = users.stream()
                .map(user->this.userToDto(user)).collect(Collectors.toList());
        logger.info("Fetched {} user", userDtos.size());
        return  userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        logger.info("Deleting user with id: {}", userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        this.userRepository.delete(user);
        logger.info("User with name: {} deleted successfully", userId);
    }

    private User dtoToUser(UserDto userDto){

//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());

        //(kis object ko convert karna h , kis class ke object me convert karna h)
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    private UserDto userToDto(User user){

        // Create a new UserDto object
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());

        //using model mapper for user to userDto Mapping (soure Object, DestinationClass.class)
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
