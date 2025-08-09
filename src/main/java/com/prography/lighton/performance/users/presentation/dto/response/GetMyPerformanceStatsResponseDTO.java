package com.prography.lighton.performance.users.presentation.dto.response;

public record GetMyPerformanceStatsResponseDTO(
        String name,
        Integer totalPerformances,
        String mostPreferredRegion
) {
    public static GetMyPerformanceStatsResponseDTO of(String name, Integer totalPerformances,
                                                      String mostPreferredRegion) {
        return new GetMyPerformanceStatsResponseDTO(name, totalPerformances, mostPreferredRegion);
    }
}
