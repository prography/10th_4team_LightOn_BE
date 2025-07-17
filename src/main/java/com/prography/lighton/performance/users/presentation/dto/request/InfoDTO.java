package com.prography.lighton.performance.users.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record InfoDTO(

        @NotBlank(message = "공연명은 필수입니다.")
        String title,

        @NotBlank(message = "공연 소개는 필수입니다.")
        String description,

        @NotNull(message = "지역 코드는 필수입니다.")
        Integer location,

        @NotBlank(message = "공연 장소는 필수입니다.")
        String place,

        String notice,

        @NotEmpty(message = "장르는 하나 이상 선택해야 합니다.")
        List<@NotNull(message = "장르는 필수입니다.") String> genre
) {
}
