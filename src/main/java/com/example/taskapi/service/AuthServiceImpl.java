package com.example.taskapi.service;

import com.example.taskapi.dto.LoginRequest;
import com.example.taskapi.entity.User;
import com.example.taskapi.exception.AlreadyExistsException;
import com.example.taskapi.repository.UserRepository;
import com.example.taskapi.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(LoginRequest loginRequest) {
        if (userRepository.existsByUsername(loginRequest.username())) {
            throw new AlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setUsername(loginRequest.username());
        user.setPassword(passwordEncoder.encode(loginRequest.password()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );
        return jwtUtil.generateToken(authentication.getName());
    }
}
