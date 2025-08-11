package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.NotAuthorizedException;

public class NotAuthorizedPerformanceException extends NotAuthorizedException {
    private static final String message = "이 공연에 대한 권한이 없습니다.";

    public NotAuthorizedPerformanceException() {
        super(message);
    }
}
