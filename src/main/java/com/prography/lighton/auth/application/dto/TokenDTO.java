package com.prography.lighton.auth.application.dto;

public record TokenDTO(
        String refreshToken,
        String accessToken
) {
}
