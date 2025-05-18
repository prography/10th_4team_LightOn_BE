package com.prography.lighton.artist.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.URL;

public record ArtistRegisterRequest(

        @NotNull(message = "아티스트 정보는 필수입니다.")
        @Valid
        ArtistDTO artist,

        @NotNull(message = "활동 이력 정보는 필수입니다.")
        @Valid
        HistoryDTO history,

        @NotBlank(message = "증빙 자료 URL은 필수입니다.")
        @URL(message = "증빙 자료는 올바른 URL 형식이어야 합니다.")
        String proof

) {
    public record ArtistDTO(

            @NotBlank(message = "아티스트 이름은 필수입니다.")
            String name,

            @NotBlank(message = "아티스트 설명은 필수입니다.")
            String description,

            @NotNull(message = "활동 지역 코드는 필수입니다.")
            Integer activityLocation,

            @NotEmpty(message = "장르는 하나 이상 선택해야 합니다.")
            List<@NotNull(message = "장르 항목은 비어 있을 수 없습니다.") Long> genre,

            @NotBlank(message = "프로필 이미지 URL은 필수입니다.")
            @URL(message = "프로필 이미지는 올바른 URL 형식이어야 합니다.")
            String profileImage

    ) {
    }

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
}
