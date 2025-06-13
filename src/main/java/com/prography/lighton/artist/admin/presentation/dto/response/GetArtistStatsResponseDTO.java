package com.prography.lighton.artist.admin.presentation.dto.response;

public record GetArtistStatsResponseDTO(
        Long totalArtistCount
) {
    public static GetArtistStatsResponseDTO of(Long totalArtistCount) {
        return new GetArtistStatsResponseDTO(totalArtistCount);
    }
}
