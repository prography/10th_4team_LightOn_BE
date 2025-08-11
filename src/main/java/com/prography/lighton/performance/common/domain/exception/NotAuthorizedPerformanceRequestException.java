package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.NotAuthorizedException;

public class NotAuthorizedPerformanceRequestException extends NotAuthorizedException {
    private static final String message = "이 공연 신청에 대한 권한이 없습니다.";

    public NotAuthorizedPerformanceRequestException() {
        super(message);
    }
}
