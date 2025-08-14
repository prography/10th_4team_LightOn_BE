package com.prography.lighton.performance.common.domain.exception;

public class DuplicatePerformanceRequestException extends DuplicatePerformanceException {
    private static final String MESSAGE = "이미 신청한 공연입니다.";

    public DuplicatePerformanceRequestException() {
        super(MESSAGE);
    }


}
