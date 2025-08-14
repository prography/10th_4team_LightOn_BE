package com.prography.lighton.performance.common.domain.exception;

public class NoSuchPerformanceRequestException extends NotFoundPerformanceException {

    private static final String MESSAGE = "존재하지 않는 공연 신청입니다.";

    public NoSuchPerformanceRequestException() {
        super(MESSAGE);
    }
}
