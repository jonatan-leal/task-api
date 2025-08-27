package com.example.taskapi.service;

import com.example.taskapi.dto.LoginRequest;
import com.example.taskapi.dto.TokenResponse;

public interface AuthService {
    String register(LoginRequest loginRequest);

    TokenResponse login(LoginRequest loginRequest);

    TokenResponse refreshToken(String refreshToken);
}
