package com.prography.lighton.member.presentation.dto.request;

import com.prography.lighton.member.validation.annotation.ValidPassword;
import jakarta.validation.constraints.Email;

public record RegisterMemberRequestDTO(
        @Email String email,
        @ValidPassword String password) {
}
