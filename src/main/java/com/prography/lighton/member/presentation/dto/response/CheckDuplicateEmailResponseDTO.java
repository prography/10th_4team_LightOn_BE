package com.prography.lighton.member.presentation.dto.response;

public record CheckDuplicateEmailResponseDTO(Boolean isDuplicate) {

    public static CheckDuplicateEmailResponseDTO of(Boolean isDuplicate) {
        return new CheckDuplicateEmailResponseDTO(isDuplicate);
    }
}
