package com.vexpress.vexpressbackend.service;

import com.vexpress.vexpressbackend.dto.LoginRequestDTO;
import com.vexpress.vexpressbackend.dto.LoginResponseDTO;

//import com.vexpress.vexpressbackend.mapper.LoginMapper;
import com.vexpress.vexpressbackend.exception.InvalidCredentialsException;
import com.vexpress.vexpressbackend.mapper.LoginMapper;
import com.vexpress.vexpressbackend.model.User;
import com.vexpress.vexpressbackend.repository.UserRepository;
import com.vexpress.vexpressbackend.response.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    private JwtService jwtService;

    public ApiResponse<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        LoginResponseDTO loginResponseDTO = loginMapper.toLoginResponseDTO(user);

        boolean isPasswordMatched =
                passwordEncoder.matches(
                        loginRequestDTO.getPassword(),
                        user.getPassword()
                );
        if (!isPasswordMatched) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        loginResponseDTO.setToken(token);
        return ApiResponse.success("Login successful", loginResponseDTO);
    }

}
