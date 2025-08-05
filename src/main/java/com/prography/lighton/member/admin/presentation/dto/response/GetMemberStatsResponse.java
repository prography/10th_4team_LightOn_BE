package com.prography.lighton.member.admin.presentation.dto.response;

public record GetMemberStatsResponse(
        Long totalMemberCount
) {
    public static GetMemberStatsResponse of(
            Long totalMemberCount
    ) {
        return new GetMemberStatsResponse(
                totalMemberCount
        );
    }
}
