package com.prography.lighton.performance.admin.presentation.dto.response;

public record GetPerformanceStatsResponseDTO(
        Long totalPerformanceCount,
        Long totalRegisteredPerformanceCount
) {

    public static GetPerformanceStatsResponseDTO of(
            Long totalPerformanceCount,
            Long totalRegisteredPerformanceCount
    ) {
        return new GetPerformanceStatsResponseDTO(
                totalPerformanceCount,
                totalRegisteredPerformanceCount
        );
    }
}
