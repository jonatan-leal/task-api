package com.example.taskapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(
        @Schema(description = "User's JWT token")
        String accessToken,
        @Schema(description = "User's refresh JWT token")
        String refreshToken
) {
}
