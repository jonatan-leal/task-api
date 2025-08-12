package com.example.taskapi.services;

import com.example.taskapi.dtos.LoginRequest;

public interface AuthService {
    String register(LoginRequest loginRequest);

    String login(LoginRequest loginRequest);
}
