package com.example.taskapi.service;

import com.example.taskapi.dto.LoginRequest;
import com.example.taskapi.dto.TokenResponse;
import com.example.taskapi.entity.User;
import com.example.taskapi.exception.AlreadyExistsException;
import com.example.taskapi.exception.InvalidTokenException;
import com.example.taskapi.repository.UserRepository;
import com.example.taskapi.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
            var username = authentication.getName();
            return new TokenResponse(
                    jwtUtil.generateAccessToken(username),
                    jwtUtil.generateRefreshToken(username)
            );
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid username or password");
        }
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException("User not found");
        }

        return new TokenResponse(
                jwtUtil.generateAccessToken(username),
                refreshToken
        );
    }
}
