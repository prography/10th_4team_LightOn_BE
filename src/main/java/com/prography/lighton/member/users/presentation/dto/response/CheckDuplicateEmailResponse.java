package com.prography.lighton.member.users.presentation.dto.response;

public record CheckDuplicateEmailResponse(Boolean isDuplicate) {

    public static CheckDuplicateEmailResponse of(Boolean isDuplicate) {
        return new CheckDuplicateEmailResponse(isDuplicate);
    }
}
