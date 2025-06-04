package com.prography.lighton.member.admin.presentation.dto.response;

public record GetMemberStatsResponseDTO(
        Long totalMemberCount,
        Long totalArtistCount,
        Long totalProceedPerformanceCount,
        Long totalRegisteredPerformanceCount
) {
    public static GetMemberStatsResponseDTO of(
            Long totalMemberCount,
            Long totalArtistCount,
            Long totalProceedPerformanceCount,
            Long totalRegisteredPerformanceCount
    ) {
        return new GetMemberStatsResponseDTO(
                totalMemberCount,
                totalArtistCount,
                totalProceedPerformanceCount,
                totalRegisteredPerformanceCount
        );
    }
}
