package com.prography.lighton.announcement.users.presentation.dto.response;

import com.prography.lighton.announcement.common.domain.entity.Announcement;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetAnnouncementListResponseDTO(Page<AnnouncementDTO> announcements) {

    public static GetAnnouncementListResponseDTO of(Page<Announcement> announcements) {
        Page<AnnouncementDTO> announcementDTOS = announcements.map(announcement ->
                AnnouncementDTO.of(
                        announcement.getId(),
                        announcement.getTitle(),
                        announcement.getContent(),
                        announcement.getImages()
                )
        );
        return new GetAnnouncementListResponseDTO(announcementDTOS);
    }

    public record AnnouncementDTO(
            Long id,
            String title,
            String content,
            List<String> imageUrls
    ) {
        public static AnnouncementDTO of(
                Long id,
                String title,
                String content,
                List<String> imageUrls
        ) {
            return new AnnouncementDTO(id, title, content, imageUrls);
        }
    }
}
