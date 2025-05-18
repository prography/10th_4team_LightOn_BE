package com.prography.lighton.performance.presentation.dto;

import com.prography.lighton.performance.domain.entity.enums.Seat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.validator.constraints.URL;

public record PerformanceRegisterRequest(

        @NotNull(message = "공연 기본 정보는 필수입니다.")
        @Valid
        Info info,

        @NotNull(message = "공연 일정 정보는 필수입니다.")
        @Valid
        Schedule schedule,

        @NotNull(message = "결제 정보는 필수입니다.")
        @Valid
        Payment payment,

        @NotEmpty(message = "좌석 유형은 하나 이상 선택해야 합니다.")
        List<@NotNull(message = "좌석 유형은 비어 있을 수 없습니다.") Seat> seat,

        @NotBlank(message = "공연 증빙 자료 URL은 필수입니다.")
        @URL(message = "공연 증빙 자료는 올바른 URL 형식이어야 합니다.")
        String proof,

        @NotBlank(message = "포스터 URL은 필수입니다.")
        @URL(message = "포스터는 올바른 URL 형식이어야 합니다.")
        String poster

) {
    public record Info(

            @NotBlank(message = "공연명은 필수입니다.")
            String title,

            @NotBlank(message = "공연 소개는 필수입니다.")
            String description,

            @NotNull(message = "지역 코드는 필수입니다.")
            Integer location,

            @NotBlank(message = "공연 장소는 필수입니다.")
            String place,

            @NotBlank(message = "입장 유의사항은 필수입니다.")
            String notice,

            @NotEmpty(message = "장르는 하나 이상 선택해야 합니다.")
            List<@NotNull(message = "장르 ID는 필수입니다.") Long> genre

    ) {
    }

    public record Schedule(

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

    public record ArtistInfo(

            @NotBlank(message = "아티스트명은 필수입니다.")
            String name,

            @NotBlank(message = "아티스트 소개는 필수입니다.")
            String description

    ) {
    }

    public record Payment(

            @NotNull(message = "비용 유뮤는 필수입니다.")
            Boolean isPaid,

            Integer price,

            String account,

            String bank,

            String accountHolder

    ) {
    }
}

