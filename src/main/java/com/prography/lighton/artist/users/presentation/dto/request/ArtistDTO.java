package com.prography.lighton.artist.users.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ArtistDTO(

        @NotBlank(message = "아티스트 이름은 필수입니다.")
        String name,

        @NotBlank(message = "아티스트 설명은 필수입니다.")
        String description,

        @NotNull(message = "활동 지역 코드는 필수입니다.")
        Integer activityLocation,

        @NotEmpty(message = "장르는 하나 이상 선택해야 합니다.")
        List<@NotNull(message = "장르 항목은 비어 있을 수 없습니다.") String> genre

) {
}