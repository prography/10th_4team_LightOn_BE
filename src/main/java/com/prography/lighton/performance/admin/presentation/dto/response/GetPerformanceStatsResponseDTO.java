package com.prography.lighton.performance.admin.presentation.dto.response;

public record GetPerformanceStatsResponseDTO(
        Long totalCompletedPerformanceCount,
        Long totalCreatedPerformanceCount
) {

    public static GetPerformanceStatsResponseDTO of(
            Long totalCompletedPerformanceCount,
            Long totalCreatedPerformanceCount
    ) {
        return new GetPerformanceStatsResponseDTO(
                totalCompletedPerformanceCount,
                totalCreatedPerformanceCount
        );
    }
}
