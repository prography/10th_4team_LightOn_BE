package com.prography.lighton.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyPhoneRequest(
        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하고 숫자 8자여야 합니다.")
        String phoneNumber,

        @NotBlank(message = "인증 코드는 필수입니다.")
        String code
) {
}
