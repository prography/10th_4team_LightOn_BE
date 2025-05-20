package com.prography.lighton.member.presentation.dto.response;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.genre.domain.entity.Genre;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public record GetPendingArtistListResponseDTO(
        Page<PendingArtistDTO> requests
) {
    public static GetPendingArtistListResponseDTO of(List<PendingArtistDTO> responses,
                                                     long totalElements,
                                                     Pageable pageable) {
        return new GetPendingArtistListResponseDTO(new PageImpl<>(responses, pageable, totalElements));
    }

    public record PendingArtistDTO(
            Long id,
            String stageName,
            ApproveStatus approveStatus,
            LocalDateTime requestedAt,
            List<Genre> genres,
            String profileImageUrl
    ) {
        public static PendingArtistDTO of(Long id, String stageName, ApproveStatus status,
                                          LocalDateTime requestedAt, List<Genre> genres, String profileImageUrl) {
            return new PendingArtistDTO(id, stageName, status, requestedAt, genres, profileImageUrl);
        }
    }
}

