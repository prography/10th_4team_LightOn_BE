package com.prography.lighton.performance.users.presentation.dto;

import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SavePerformanceRequest(

        List<@NotNull(message = "아티스트 ID에는 빈 값이 들어갈 수 없습니다.") Long> artists,

        @NotNull(message = "공연 기본 정보는 필수입니다.")
        @Valid
        InfoDTO info,

        @NotNull(message = "공연 일정 정보는 필수입니다.")
        @Valid
        ScheduleDTO schedule,

        @NotNull(message = "결제 정보는 필수입니다.")
        @Valid
        PaymentDTO payment,

        @NotEmpty(message = "좌석 유형은 하나 이상 선택해야 합니다.")
        List<@NotNull(message = "좌석 유형은 비어 있을 수 없습니다.") Seat> seat,

        Integer totalSeatsCount

) {
}

