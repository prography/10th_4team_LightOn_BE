package com.prography.lighton.performance.users.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record UserBuskingRegisterRequest(

        @NotNull(message = "공연 기본 정보는 필수입니다.")
        @Valid
        InfoDTO info,

        @NotNull(message = "공연 일정 정보는 필수입니다.")
        @Valid
        ScheduleDTO schedule,

        @NotBlank(message = "공연 증빙 자료 URL은 필수입니다.")
        @URL(message = "공연 증빙 자료는 올바른 URL 형식이어야 합니다.")
        String proof,

        @NotBlank(message = "야티스트 이름은 필수입니다.")
        String artistName,

        @NotBlank(message = "아티스트 설명은 필수입니다.")
        String artistDescription

) {
}

