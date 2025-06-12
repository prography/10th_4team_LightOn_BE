package com.prography.lighton.announcement.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ManageAnnouncementRequestDTO(
        @NotBlank(message = "공지사항 제목은 필수입니다.") String title,
        @NotBlank(message = "공지사항 제목은 필수입니다.") String content,
        List<String> imageUrls
) {
}
