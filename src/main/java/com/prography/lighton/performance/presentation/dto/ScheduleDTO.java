package com.prography.lighton.performance.presentation.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDTO(

        @NotNull(message = "공연 시작 날짜는 필수입니다.")
        LocalDate startDate,

        @NotNull(message = "공연 종료 날짜는 필수입니다.")
        LocalDate endDate,

        @NotNull(message = "공연 시작 시간은 필수입니다.")
        LocalTime startTime,

        @NotNull(message = "공연 종료 시간은 필수입니다.")
        LocalTime endTime

) {
}
