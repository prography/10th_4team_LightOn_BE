package com.prography.lighton.member.presentation.dto.response;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetPendingArtistListResponseDTO(
        Page<PendingArtistDTO> requests
) {
    public static GetPendingArtistListResponseDTO of(Page<PendingArtistDTO> requests) {
        return new GetPendingArtistListResponseDTO(requests);
    }

    public record PendingArtistDTO(
            Long id,
            String stageName,
            ApproveStatus approveStatus,
            LocalDateTime requestedAt,
            List<String> genres,
            String profileImageUrl
    ) {
        public static PendingArtistDTO of(Long id, String stageName, ApproveStatus status,
                                          LocalDateTime requestedAt, List<String> genres, String profileImageUrl) {
            return new PendingArtistDTO(id, stageName, status, requestedAt, genres, profileImageUrl);
        }
    }
}

