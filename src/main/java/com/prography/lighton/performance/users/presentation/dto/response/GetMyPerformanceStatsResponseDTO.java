package com.prography.lighton.performance.users.presentation.dto.response;

public record GetMyPerformanceStatsResponseDTO(
        Integer totalPerformances,
        String mostPreferredRegion
) {
    public static GetMyPerformanceStatsResponseDTO of(Integer totalPerformances, String mostPreferredRegion) {
        return new GetMyPerformanceStatsResponseDTO(totalPerformances, mostPreferredRegion);
    }
}
