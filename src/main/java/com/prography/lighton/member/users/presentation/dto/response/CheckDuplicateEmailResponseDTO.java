package com.prography.lighton.member.users.presentation.dto.response;

public record CheckDuplicateEmailResponseDTO(Boolean isDuplicate) {

    public static CheckDuplicateEmailResponseDTO of(Boolean isDuplicate) {
        return new CheckDuplicateEmailResponseDTO(isDuplicate);
    }
}
