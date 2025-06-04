package com.prography.lighton.announcement.users.presentation.dto.response;

public record GetAnnouncementDetailResponseDTO(
        Long id,
        String title,
        String content,
        String imageUrl
) {
    public static GetAnnouncementDetailResponseDTO of(
            Long id,
            String title,
            String content,
            String imageUrl
    ) {
        return new GetAnnouncementDetailResponseDTO(id, title, content, imageUrl);
    }
}
