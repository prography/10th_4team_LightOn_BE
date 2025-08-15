package com.prography.lighton.performance.common.domain.exception;

public class NotAuthorizedPerformanceRequestException extends NotAuthorizedPerformanceException {
    private static final String message = "이 공연 신청에 대한 권한이 없습니다.";

    public NotAuthorizedPerformanceRequestException() {
        super(message);
    }
}
