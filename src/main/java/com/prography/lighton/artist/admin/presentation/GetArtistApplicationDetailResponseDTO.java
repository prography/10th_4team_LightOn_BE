package com.prography.lighton.artist.admin.presentation;

import com.prography.lighton.artist.admin.domain.enums.ApproveStatus;
import java.util.List;

public record GetArtistApplicationDetailResponseDTO(
        Long id,
        ApproveStatus approveStatus,
        String stageName,
        String description,
        Integer activityLocation,
        List<String> genres,
        String bio,
        List<String> activityImages,
        String proof
) {
    public static GetArtistApplicationDetailResponseDTO of(
            Long id,
            ApproveStatus approveStatus,
            String stageName,
            String description,
            Integer activityLocation,
            List<String> genres,
            String bio,
            List<String> activityImages,
            String proof
    ) {
        return new GetArtistApplicationDetailResponseDTO(
                id,
                approveStatus,
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
