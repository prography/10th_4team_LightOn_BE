package com.prography.lighton.member.admin.presentation.dto.response;

public record GetMemberStatsResponseDTO(
        Long totalMemberCount
) {
    public static GetMemberStatsResponseDTO of(
            Long totalMemberCount
    ) {
        return new GetMemberStatsResponseDTO(
                totalMemberCount
        );
    }
}
