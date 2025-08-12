package com.example.taskapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "User's JWT token")
        String token
        ) {
}
