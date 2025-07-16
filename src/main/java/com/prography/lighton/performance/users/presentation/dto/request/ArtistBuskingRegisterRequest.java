package com.prography.lighton.performance.users.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ArtistBuskingRegisterRequest(

        @NotNull(message = "공연 기본 정보는 필수입니다.")
        @Valid
        InfoDTO info,

        @NotNull(message = "공연 일정 정보는 필수입니다.")
        @Valid
        ScheduleDTO schedule

) {
}

