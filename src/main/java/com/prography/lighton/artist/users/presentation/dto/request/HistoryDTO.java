package com.prography.lighton.artist.users.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.URL;

public record HistoryDTO(

        @NotBlank(message = "주요 활동 이력은 필수입니다.")
        String bio,

        @Size(max = 5, message = "활동 사진은 최대 5장까지 업로드 가능합니다.")
        List<
                @NotBlank(message = "활동 사진 URL은 비어 있을 수 없습니다.")
                @URL(message = "활동 사진은 올바른 URL 형식이어야 합니다.")
                        String
                > activityPhotos

) {
}