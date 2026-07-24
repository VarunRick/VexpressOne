package com.vexpress.vexpressbackend.service;

import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;
import com.vexpress.vexpressbackend.dto.UserRequestDTO;
import com.vexpress.vexpressbackend.dto.UserResponseDTO;
import com.vexpress.vexpressbackend.mapper.UserMapper;

import com.vexpress.vexpressbackend.model.User;
import com.vexpress.vexpressbackend.repository.UserRepository;

import com.vexpress.vexpressbackend.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vexpress.vexpressbackend.exception.UserNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Register User//
    public ApiResponse<UserResponseDTO> registerUser(UserRequestDTO userRequestDTO) {
        logger.info("User registration request received.");
        logger.debug("User Request: {}", userRequestDTO);
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getId());
        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(savedUser);
        return ApiResponse.success( "User registered successfully", userResponseDTO);
    }

    //Register All Users//
    public ApiResponse<List <UserResponseDTO>> registerAll(List<UserRequestDTO> userRequestDTOs) {
        List<User> users = userRequestDTOs.stream()
                .map(userMapper::toEntity)
                .toList();
        users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        List<User> savedUsers = userRepository.saveAll(users);
        List<UserResponseDTO> response = savedUsers.stream()
                .map(userMapper::toResponseDTO)
                .toList();
        return ApiResponse.success("Users registered successfully", response);
    }

    //Get User By Id //
    public ApiResponse<UserResponseDTO> getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        logger.info("User found with ID: {}", id);
        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(user);
        return ApiResponse.success( "User fetched successfully", userResponseDTO);
    }

    //Get all users//
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        logger.info("Total users found: {}", users.size());
        List<UserResponseDTO> response = users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
        return ApiResponse.success(
                "Users fetched successfully",
                response
        );
    }

    //update by Id
    public ApiResponse<UserResponseDTO> updateUser(Long id, UserRequestDTO userRequestDTO) {
        logger.info("Fetching user with ID: {}", id);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        userMapper.updateUserFromDTO(userRequestDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return ApiResponse.success("User updated successfully", userMapper.toResponseDTO(updatedUser));
    }

    //Delete User//
    public ApiResponse<UserResponseDTO> deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);

        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        userRepository.delete(existingUser);
        return ApiResponse.success("User deleted successfully", userMapper.toResponseDTO(existingUser));
    }

    //Delete All User in DB//
    public ApiResponse<UserResponseDTO> deleteAllUsers() {
        userRepository.deleteAll();
        return ApiResponse.success("All users deleted successfully", null);
    }
}
