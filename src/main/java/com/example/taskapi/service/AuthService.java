package com.example.taskapi.service;

import com.example.taskapi.dto.LoginRequest;

public interface AuthService {
    String register(LoginRequest loginRequest);

    String login(LoginRequest loginRequest);
}
