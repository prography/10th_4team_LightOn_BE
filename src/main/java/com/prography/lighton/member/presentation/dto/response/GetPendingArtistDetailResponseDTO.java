package com.prography.lighton.member.presentation.dto.response;

import com.prography.lighton.genre.domain.entity.Genre;
import java.util.List;

public record GetPendingArtistDetailResponseDTO(
        Long id,
        String stageName,
        String description,
        Integer activityLocation,
        List<Genre> genres,
        String profileImageUrl,
        String bio,
        List<String> activityImages,
        String proof
) {
    public static GetPendingArtistDetailResponseDTO of(
            Long id,
            String stageName,
            String description,
            Integer activityLocation,
            List<Genre> genres,
            String profileImageUrl,
            String bio,
            List<String> activityImages,
            String proof
    ) {
        return new GetPendingArtistDetailResponseDTO(
                id,
                stageName,
                description,
                activityLocation,
                genres,
                profileImageUrl,
                bio,
                activityImages,
                proof
        );
    }
}
