package com.prography.lighton.performance.common.domain.exception;

public class PerformanceNotApprovedException extends BusinessConflictPerformanceException {
    private static final String message = "승인되지 않은 공연입니다.";

    public PerformanceNotApprovedException() {
        super(message);
    }
}
