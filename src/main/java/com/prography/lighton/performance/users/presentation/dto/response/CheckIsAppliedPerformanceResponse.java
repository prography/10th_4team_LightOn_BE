package com.prography.lighton.performance.users.presentation.dto.response;

public record CheckIsAppliedPerformanceResponse(
        boolean isApplied
) {
    public static CheckIsAppliedPerformanceResponse of(boolean isApplied) {
        return new CheckIsAppliedPerformanceResponse(isApplied);
    }
}
