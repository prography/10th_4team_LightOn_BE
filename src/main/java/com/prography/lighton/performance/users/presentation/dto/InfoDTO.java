package com.prography.lighton.performance.users.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.validator.constraints.URL;

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
        List<@NotNull(message = "장르는 필수입니다.") String> genre,

        @URL(message = "포스터는 올바른 URL 형식이어야 합니다.")
        String poster
) {
}
