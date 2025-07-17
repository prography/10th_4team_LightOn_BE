package com.prography.lighton.artist.users.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record HistoryDTO(

        @NotBlank(message = "주요 활동 이력은 필수입니다.")
        String bio

) {
}