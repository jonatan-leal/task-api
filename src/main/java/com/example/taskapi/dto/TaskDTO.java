package com.example.taskapi.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TaskDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @NotBlank(message = "Title is mandatory")
        String title,
        String description,
        boolean completed
) {
}