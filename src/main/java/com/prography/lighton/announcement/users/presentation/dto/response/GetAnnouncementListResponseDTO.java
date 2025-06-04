package com.prography.lighton.announcement.users.presentation.dto.response;

import com.prography.lighton.announcement.common.domain.entity.Announcement;
import org.springframework.data.domain.Page;

public record GetAnnouncementListResponseDTO(Page<AnnouncementDTO> announcements) {

    public static GetAnnouncementListResponseDTO of(Page<Announcement> announcements) {
        Page<AnnouncementDTO> announcementDTOS = announcements.map(announcement ->
                AnnouncementDTO.of(
                        announcement.getId(),
                        announcement.getTitle(),
                        announcement.getContent(),
                        announcement.getImageUrl()
                )
        );
        return new GetAnnouncementListResponseDTO(announcementDTOS);
    }

    public record AnnouncementDTO(
            Long id,
            String title,
            String content,
            String imageUrl
    ) {
        public static AnnouncementDTO of(
                Long id,
                String title,
                String content,
                String imageUrl
        ) {
            return new AnnouncementDTO(id, title, content, imageUrl);
        }
    }
}
