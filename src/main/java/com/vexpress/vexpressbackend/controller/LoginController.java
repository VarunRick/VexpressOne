package com.vexpress.vexpressbackend.controller;

import com.vexpress.vexpressbackend.dto.LoginRequestDTO;
import com.vexpress.vexpressbackend.dto.LoginResponseDTO;

import com.vexpress.vexpressbackend.response.ApiResponse;
import com.vexpress.vexpressbackend.service.LoginService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ApiResponse<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        return loginService.login(loginRequestDTO);

    }
}
