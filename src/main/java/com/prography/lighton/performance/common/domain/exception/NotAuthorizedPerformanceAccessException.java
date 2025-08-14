package com.prography.lighton.performance.common.domain.exception;

public class NotAuthorizedPerformanceAccessException extends NotAuthorizedPerformanceException {
    private static final String message = "이 공연에 대한 권한이 없습니다.";

    public NotAuthorizedPerformanceAccessException() {
        super(message);
    }
}
