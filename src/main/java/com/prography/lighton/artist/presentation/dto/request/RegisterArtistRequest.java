package com.prography.lighton.artist.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record RegisterArtistRequest(

        @NotNull(message = "아티스트 정보는 필수입니다.")
        @Valid
        ArtistDTO artist,

        @NotNull(message = "활동 이력 정보는 필수입니다.")
        @Valid
        HistoryDTO history,

        @NotBlank(message = "증빙 자료 URL은 필수입니다.")
        @URL(message = "증빙 자료는 올바른 URL 형식이어야 합니다.")
        String proof

) {
}
