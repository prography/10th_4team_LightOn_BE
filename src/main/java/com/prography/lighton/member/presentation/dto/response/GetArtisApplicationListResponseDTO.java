package com.prography.lighton.member.presentation.dto.response;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetArtisApplicationListResponseDTO(
        Page<ArtistApplicationDTO> requests
) {
    public static GetArtisApplicationListResponseDTO of(Page<ArtistApplicationDTO> requests) {
        return new GetArtisApplicationListResponseDTO(requests);
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

