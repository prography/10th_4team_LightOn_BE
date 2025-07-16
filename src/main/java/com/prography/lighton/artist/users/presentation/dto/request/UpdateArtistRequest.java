package com.prography.lighton.artist.users.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateArtistRequest(

        @NotNull(message = "아티스트 정보는 필수입니다.")
        @Valid
        ArtistDTO artist,

        @NotNull(message = "활동 이력 정보는 필수입니다.")
        @Valid
        HistoryDTO history,

        Integer index

) {
}
