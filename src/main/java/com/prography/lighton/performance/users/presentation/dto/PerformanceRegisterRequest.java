package com.prography.lighton.performance.users.presentation.dto;

import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.validator.constraints.URL;

public record PerformanceRegisterRequest(

        @NotEmpty(message = "아티스트는 하나 이상 선택해야 합니다.")
        List<@NotNull(message = "아티스트 ID는 필수입니다.") Long> artists,

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

        @NotBlank(message = "공연 증빙 자료 URL은 필수입니다.")
        @URL(message = "공연 증빙 자료는 올바른 URL 형식이어야 합니다.")
        String proof,

        Integer totalSeatsCount

) {
}

