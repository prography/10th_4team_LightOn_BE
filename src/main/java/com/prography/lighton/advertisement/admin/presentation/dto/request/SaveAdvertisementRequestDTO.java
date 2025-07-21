package com.prography.lighton.advertisement.admin.presentation.dto.request;


import com.prography.lighton.advertisement.common.domain.entity.enums.Position;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SaveAdvertisementRequestDTO(

        @NotNull(message = "광고 및 배너 이미지 위치는 필수입니다.")
        Position position,

        @Min(value = 1, message = "디스플레이 순서는 1 이상이어야 합니다.")
        int displayOrder,

        String linkUrl,
        String title,
        String content
) {
}
