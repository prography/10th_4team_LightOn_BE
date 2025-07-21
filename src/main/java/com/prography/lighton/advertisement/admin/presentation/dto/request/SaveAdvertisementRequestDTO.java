package com.prography.lighton.advertisement.admin.presentation.dto.request;


import com.prography.lighton.advertisement.common.domain.entity.enums.Position;

public record SaveAdvertisementRequestDTO(
        Position position,
        int displayOrder,
        String linkUrl,
        String title,
        String content
) {
}
