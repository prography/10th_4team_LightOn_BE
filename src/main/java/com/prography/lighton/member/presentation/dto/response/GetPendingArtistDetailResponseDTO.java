package com.prography.lighton.member.presentation.dto.response;

import java.util.List;

public record GetPendingArtistDetailResponseDTO(
        Long id,
        String stageName,
        String description,
        Integer activityLocation,
        List<String> genres,
        String bio,
        List<String> activityImages,
        String proof
) {
    public static GetPendingArtistDetailResponseDTO of(
            Long id,
            String stageName,
            String description,
            Integer activityLocation,
            List<String> genres,
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
                bio,
                activityImages,
                proof
        );
    }
}
