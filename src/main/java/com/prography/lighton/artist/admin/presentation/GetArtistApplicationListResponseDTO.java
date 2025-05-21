package com.prography.lighton.artist.admin.presentation;

import com.prography.lighton.artist.admin.domain.enums.ApproveStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetArtistApplicationListResponseDTO(
        Page<ArtistApplicationDTO> requests
) {
    public static GetArtistApplicationListResponseDTO of(Page<ArtistApplicationDTO> requests) {
        return new GetArtistApplicationListResponseDTO(requests);
    }

    public record ArtistApplicationDTO(
            Long id,
            String stageName,
            ApproveStatus approveStatus,
            LocalDateTime requestedAt,
            List<String> genres,
            String profileImageUrl
    ) {
        public static ArtistApplicationDTO of(Long id, String stageName, ApproveStatus status,
                                              LocalDateTime requestedAt, List<String> genres, String profileImageUrl) {
            return new ArtistApplicationDTO(id, stageName, status, requestedAt, genres, profileImageUrl);
        }
    }
}

