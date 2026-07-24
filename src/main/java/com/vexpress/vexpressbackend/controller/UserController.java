package com.vexpress.vexpressbackend.controller;

import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;
import com.vexpress.vexpressbackend.dto.UserRequestDTO;
import com.vexpress.vexpressbackend.dto.UserResponseDTO;
import com.vexpress.vexpressbackend.response.ApiResponse;
import com.vexpress.vexpressbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ApiResponse<UserResponseDTO> registerUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/registerAllUsers")
    public ApiResponse<List<UserResponseDTO>> registerAllUsers(@Valid @RequestBody List<UserRequestDTO> userRequestDTOs) {
        return userService.registerAll(userRequestDTOs);
    }

    //Test API Request: GET - http://localhost:8080/user/1
    @GetMapping("/user/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Long id) {

        return userService.getUserById(id);

    }

    //Test API request: GET http://localhost:8080/users
    @GetMapping("/users")
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {

        return userService.getAllUsers();
    }

    //Test API request: PUT http://localhost:8080/user/1
    @PutMapping("/user/{id}")
    public ApiResponse<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(id, userRequestDTO);
    }

    //Test API request: DELETE http://localhost:8080/user/1
    @DeleteMapping("/user/{id}")
    public ApiResponse<UserResponseDTO> deleteUser(@Valid @PathVariable Long id) {
        return userService.deleteUser(id);
    }

    //Test API request: DELETE http://localhost:8080/users/deleteAll
    @DeleteMapping("/users/deleteAll")
    public ApiResponse<UserResponseDTO> deleteAllUsers() {
        return userService.deleteAllUsers();
    }
}
