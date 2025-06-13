package com.prography.lighton.announcement.users.presentation.dto.response;

import java.util.List;

public record GetAnnouncementDetailResponseDTO(
        Long id,
        String title,
        String content,
        List<String> imageUrls
) {
    public static GetAnnouncementDetailResponseDTO of(
            Long id,
            String title,
            String content,
            List<String> imageUrls
    ) {
        return new GetAnnouncementDetailResponseDTO(id, title, content, imageUrls);
    }
}
